package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.solutionchallenge.sharecourseandbook.Model.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.Model.StudentUser
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.online_course_request_fragment.*

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


        var currency="₺"
        currncySpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                currency=parent?.getItemAtPosition(position).toString()
            }

        }

        btnSendCourseRequest.setOnClickListener {
            var course=etCourseName.text.toString()
            var courseLink=etCourseLink.text.toString()
            var price=etPrice.text.toString()

            var numberOfRequest:Int
            if(course.isEmpty()||courseLink.isEmpty()||price.isEmpty()){
                Toast.makeText(this.context,"Plase Fill In The Blanks",Toast.LENGTH_SHORT).show()
            }
            else{
                var pricex=price.toDouble()
                viewModel.getStudentWithMail(auth.currentUser?.email.toString()).observe(viewLifecycleOwner,
                    Observer {
                        numberOfRequest=it.numberOfRequest
                        if(numberOfRequest<2){
                            requestApproveDialog(this.context,course,courseLink,pricex,currency,"udemy")
                        }
                        else
                            Toast.makeText(this.context,"You have not enough credit to make a request",Toast.LENGTH_SHORT).show()
                    })
            }



        }





    }

    fun requestApproveDialog(context: Context?,courseName: String,courseLink: String,coursePrice: Double,currency: String,platformName: String){
        var dialog= AlertDialog.Builder(context).
        setTitle("Are you sure ?").
        setMessage("If you click yes button you will send a request us . When we approve your request we will notify with email").
        setIcon(R.drawable.ic_waring).
        setPositiveButton("Yes"){_,_->
            makeRequest(courseName, courseLink, coursePrice,currency,platformName)
        }.
        setNegativeButton("No"){_,_->

        }.create()

        dialog.show()

    }


    fun makeRequest(courseName:String,courseLink:String,coursePrice:Double,currency:String,platformName:String){
        var firstName=sharedPreferences.getString("firstName","").toString()
        var lastName=sharedPreferences.getString("lastName","").toString()
        var major=sharedPreferences.getString("major","").toString()
        var school=sharedPreferences.getString("school","").toString()
        var country=sharedPreferences.getString("country","").toString()
        var studentUser=StudentUser(auth.currentUser?.email.toString(),firstName,lastName,school,major,country)
        var request=OnlineCourseRequest(platformName = platformName,courseName = courseName,courseLink = courseLink,price = coursePrice,userMail = auth.currentUser!!.email.toString(),currency = currency,studentUser = studentUser )
        viewModel.incrementNumberOfRequestFieldİWthEmail(auth.currentUser?.email.toString())
        viewModel.makeRequest(request)
        Navigation.findNavController(view!!).navigate(R.id.action_onlineCourseFragment_to_profileFragment)
    }

}