package com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.RemoteApi.RetrofitObject
import kotlinx.android.synthetic.main.course_request.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class myRequestsAdapter(var list: List<OnlineCourseRequest>,var contextx: Context,var viewx:View) : RecyclerView.Adapter<myRequestsAdapter.myRequestsViewHolder>() {


    class myRequestsViewHolder(item: View) :RecyclerView.ViewHolder(item)





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myRequestsViewHolder {
        var view=LayoutInflater.from(contextx).inflate(R.layout.course_request,parent,false)
        return myRequestsViewHolder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: myRequestsViewHolder, position: Int) {


        var request = list.get(position)
        holder.itemView.apply {
            CoroutineScope(Dispatchers.IO).launch {
                var course =
                    RetrofitObject.apiService.getCourse(takeIDFromUrl(request.courseLink)).body()
                withContext(Dispatchers.Main) {
                    tvCourseName.text = course?.title
                    tvMajorAndCountry.text =
                        "Student from " + request.studentUser.country.toUpperCase()
                    Glide.with(viewx).load(course?.image_480x270).into(imageView4)
                    var cuurency = course?.price_detail?.currency_symbol
                    if (cuurency == "₺")
                        tvCoursePrice.text = "36.90₺"
                    else if (cuurency == "€")
                        tvCoursePrice.text = "11.90€"
                    else
                        tvCoursePrice.text = "11.90$"
                }

            }

            btnDonate.visibility=View.INVISIBLE
        }



    }



    //Helper Functions
    fun takeIDFromUrl(url: String): Int {
        var numberOfSlash = 0
        var id: StringBuilder = StringBuilder("")
        for (char in url) {
            if (char == '/')
                numberOfSlash++
            else if (numberOfSlash == 7) {
                id.append(char)
            }
        }
        try {
            var idx = id.toString().toInt()
            return idx
        } catch (e: Exception) {
            Log.d("Error", "${e.message}")
            return 0
        }
    }
}