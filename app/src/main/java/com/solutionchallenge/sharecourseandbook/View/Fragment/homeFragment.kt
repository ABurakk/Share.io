package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.onlineCourseAdapter
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class homeFragment :Fragment(R.layout.home_fragment) {

    lateinit var viewModel: FirestoreViewModel
    lateinit var auth:FirebaseAuth
    lateinit var adapter: onlineCourseAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var circularprogress:CircularProgressDrawable


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        auth=(activity as MainActivity).auth
        sharedPreferences=(activity as MainActivity).sharedPreferences
        var array= resources.getStringArray(R.array.countries_array)


        setProgress()

        adapter= onlineCourseAdapter(view,circularprogress,requireContext(), listOf(),view)
        rvCourseRequests.adapter=adapter
        rvCourseRequests.layoutManager=LinearLayoutManager(this.context)


           Firebase.firestore.collection("request").get().addOnSuccessListener {

               var list=it.toObjects<OnlineCourseRequest>()

               var list2=list.sortedBy { it.studentUser.priorityPoint }

               adapter.list=list2

               Log.d("belhanda",list2.size.toString())

               adapter.notifyDataSetChanged()

           }.addOnFailureListener {
               Toast.makeText(context,"${it.message}",Toast.LENGTH_SHORT).show()
           }


        btnShuffle.setOnClickListener {

        }


        btnFilterByCountry.setOnClickListener {

        }





    }

    fun setProgress(){
        circularprogress = CircularProgressDrawable(requireContext())
        circularprogress.strokeWidth = 5f
        circularprogress.centerRadius = 30f
        circularprogress.start()
    }

}