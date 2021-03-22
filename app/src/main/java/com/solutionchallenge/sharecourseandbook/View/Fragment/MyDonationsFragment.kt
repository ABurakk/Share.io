package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.myRequestsAdapter
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.onlineCourseAdapter
import kotlinx.android.synthetic.main.my_donations_fragment.*

class MyDonationsFragment :Fragment(R.layout.my_donations_fragment) {


    lateinit var adapter: myRequestsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



          btnBackToProfile3.setOnClickListener {
              Navigation.findNavController(requireView()).navigate(R.id.action_myDonationsFragment_to_profileFragment)
          }


    }
}