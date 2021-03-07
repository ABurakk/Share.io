package com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Fragment.homeFragmentDirections
import kotlinx.android.synthetic.main.course_request.view.*

class onlineCourseAdapter(var context: Context, var list: List<OnlineCourseRequest>, var view:View) : RecyclerView.Adapter<onlineCourseAdapter.courseRequestVieHolder>() {

   class courseRequestVieHolder(itemView:View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): courseRequestVieHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.course_request,parent,false)
        return courseRequestVieHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: courseRequestVieHolder, position: Int) {

       holder.itemView.apply {

       }
        holder.itemView.btnDonate.setOnClickListener {

        }

    }
}