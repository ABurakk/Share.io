package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.BillingClient
import com.solutionchallenge.sharecourseandbook.Model.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.onlineCourseAdapter
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.home_fragment.*

class homeFragment :Fragment(R.layout.home_fragment) {

    lateinit var viewModel: FirestoreViewModel
    lateinit var auth:FirebaseAuth
    lateinit var adapter: onlineCourseAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        auth=(activity as MainActivity).auth
        sharedPreferences=(activity as MainActivity).sharedPreferences


        adapter= onlineCourseAdapter(this.context!!, listOf(),view)
        rvCourseRequests.adapter=adapter
        rvCourseRequests.layoutManager=LinearLayoutManager(this.context)
        Firebase.firestore.collection("request").get().addOnSuccessListener {
            var list=it.toObjects<OnlineCourseRequest>()
            adapter.list=list
            adapter.notifyDataSetChanged()
        }.addOnFailureListener {

        }



    }



}