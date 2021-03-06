package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth

class bookRequestFragment :Fragment() {
    lateinit var auth:FirebaseAuth
    lateinit var viewModel:FirestoreViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=(activity as MainActivity).auth
        viewModel=(activity as MainActivity).viewModel
    }
}