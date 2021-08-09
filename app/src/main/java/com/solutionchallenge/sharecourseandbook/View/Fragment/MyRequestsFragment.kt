package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.myRequestsAdapter
import kotlinx.android.synthetic.main.my_requests_fragment.*

class MyRequestsFragment :Fragment(R.layout.my_requests_fragment) {


    lateinit var adapter: myRequestsAdapter
    lateinit var auth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=(activity as MainActivity).auth


        btnBackToProfile4.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_myRequestsFragment_to_profileFragment)
        }


        adapter= myRequestsAdapter(listOf(),requireContext(),requireView())
        myRequestRV.adapter=adapter
        myRequestRV.layoutManager= LinearLayoutManager(requireContext())


        Firebase.firestore.collection("request").whereEqualTo("userMail",auth.currentUser?.email.toString()).get().addOnSuccessListener {
            var list=it.toObjects<OnlineCourseRequest>()
            adapter.list=list
            adapter.notifyDataSetChanged()
        }
    }
}