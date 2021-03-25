package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.myRequestsAdapter
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.onlineCourseAdapter
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import kotlinx.android.synthetic.main.my_donations_fragment.*

class MyDonationsFragment :Fragment(R.layout.my_donations_fragment) {

    lateinit var viewModel: FirestoreViewModel
    lateinit var auth: FirebaseAuth
    lateinit var adapter: myRequestsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=(activity as MainActivity).viewModel
        auth=(activity as MainActivity).auth



          btnBackToProfile3.setOnClickListener {
              Navigation.findNavController(requireView()).navigate(R.id.action_myDonationsFragment_to_profileFragment)
          }


    }
}