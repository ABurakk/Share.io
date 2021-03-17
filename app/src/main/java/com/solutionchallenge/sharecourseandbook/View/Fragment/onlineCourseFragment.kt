package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StudentUser
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.solutionchallenge.sharecourseandbook.RemoteApi.RetrofitObject
import kotlinx.android.synthetic.main.online_course_request_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class onlineCourseFragment :Fragment(R.layout.online_course_request_fragment) {

    lateinit var auth: FirebaseAuth
    lateinit var viewModel: FirestoreViewModel
    lateinit var intentx:Intent
    lateinit var sharedPreferences: SharedPreferences
    var studentCollection=Firebase.firestore.collection("student")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=(activity as MainActivity).auth
        viewModel=(activity as MainActivity).viewModel
        intentx=(activity as MainActivity).intentx
        sharedPreferences=(activity as MainActivity).sharedPreferences


        var string=sharedPreferences.getString("firstName","defVal")
        btnSendCourseRequest.visibility=View.INVISIBLE

        val circularProgress = CircularProgressDrawable(requireContext())
        circularProgress.strokeWidth = 5f
        circularProgress.centerRadius = 30f
        circularProgress.start()

        var numberOfRequest=0
        viewModel.getStudentWithMail(auth.currentUser?.email.toString()).observe(viewLifecycleOwner,
            Observer {
                numberOfRequest=it.numberOfRequest
            })

        btnTakeThisCourse.setOnClickListener {
            var url=etCourseName.text.toString()
            var courseId=takeIDFromUrl(url)
             if(courseId!=0){
                 CoroutineScope(Dispatchers.IO).launch {
                     var course=RetrofitObject.apiService.getCourse(courseId).body()

                     withContext(Dispatchers.Main){
                         tvCourseNamex.text=course?.title
                         tvCreator.text="Created by  "+course?.visible_instructors?.get(0)?.display_name
                         Glide.with(this@onlineCourseFragment).load(course?.image_240x135).placeholder(circularProgress).into(imageView2)
                         var cuurency=course?.price_detail?.currency_symbol
                         if(cuurency=="₺")
                             tvCoursePricex.text="27.90₺"
                         else if(cuurency=="€")
                             tvCoursePricex.text="11.90€"
                         else
                             tvCoursePricex.text="11.90$"


                         btnSendCourseRequest.visibility=View.VISIBLE
                     }

                 }
             }else{

             }

        }


        btnSendCourseRequest.setOnClickListener {

            if(numberOfRequest<2){
                var url=etCourseName.text.toString()
                requestApproveDialog(requireContext(),url)
            }
            else{
                Toast.makeText(requireContext(),"You have not enough credit",Toast.LENGTH_LONG).show()
            }

        }





    }

    fun requestApproveDialog(context: Context?,courseLink: String){
        var dialog= AlertDialog.Builder(context).
        setTitle("Are you sure ?").
        setMessage("If you click yes button you will send a request us . When we approve your request we will notify with email").
        setIcon(R.drawable.ic_waring).
        setPositiveButton("Yes"){_,_->
            makeRequest(courseLink)
        }.
        setNegativeButton("No"){_,_->

        }.create()

        dialog.show()

    }


    fun makeRequest(courseLink: String){
        var firstName=sharedPreferences.getString("firstName","").toString()
        var lastName=sharedPreferences.getString("lastName","").toString()
        var major=sharedPreferences.getString("major","").toString()
        var school=sharedPreferences.getString("school","").toString()
        var country=sharedPreferences.getString("country","").toString()
        var studentUser= StudentUser(auth.currentUser?.email.toString(), firstName, lastName, school, major, country)
        var request=OnlineCourseRequest(auth.currentUser!!.email.toString(),courseLink,studentUser,false,country)
        viewModel.makeRequest(request)
        viewModel.incrementNumberOfRequestFieldİWthEmail(auth.currentUser?.email.toString())
        Navigation.findNavController(requireView()).navigate(R.id.action_onlineCourseFragment_to_profileFragment)
    }





    fun takeIDFromUrl(url:String):Int{
        var numberOfSlash=0
        var id:StringBuilder=StringBuilder("")
        for(char in url){
            if(char=='/')
                numberOfSlash++
            else if(numberOfSlash==7){
                id.append(char)
            }
        }
        try {
            var idx=id.toString().toInt()
            return idx
        }catch (e :Exception){
            Toast.makeText(requireContext(),"This link format is not true",Toast.LENGTH_LONG).show()
            return 0
        }
    }

}