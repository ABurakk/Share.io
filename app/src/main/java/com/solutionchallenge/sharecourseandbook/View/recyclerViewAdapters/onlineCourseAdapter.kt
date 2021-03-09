package com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.RemoteApi.RetrofitObject
import com.solutionchallenge.sharecourseandbook.View.Fragment.homeFragmentDirections
import kotlinx.android.synthetic.main.course_request.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class onlineCourseAdapter(var viewx:View,var circularProgressDrawable: CircularProgressDrawable,var context: Context, var list: List<OnlineCourseRequest>, var view:View) : RecyclerView.Adapter<onlineCourseAdapter.courseRequestVieHolder>() {

   class courseRequestVieHolder(itemView:View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): courseRequestVieHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.course_request,parent,false)
        return courseRequestVieHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: courseRequestVieHolder, position: Int) {

        var request=list.get(position)
       holder.itemView.apply {
         CoroutineScope(Dispatchers.IO).launch {
             var course=RetrofitObject.apiService.getCourse(takeIDFromUrl(request.courseLink)).body()
             withContext(Dispatchers.Main){
                 tvCourseName.text=course?.title
                 tvMajorAndCountry.text="Student from "+request.studentUser.country.toUpperCase()
                 Glide.with(view).load(course?.image_480x270).placeholder(circularProgressDrawable).into(imageView4)
                 var cuurency=course?.price_detail?.currency_symbol
                 if(cuurency=="₺")
                     tvCoursePrice.text="27.90₺"
                 else if(cuurency=="€")
                     tvCoursePrice.text="11.90€"
                 else
                     tvCoursePrice.text="11.90$"
             }

         }
           btnDonate.setOnClickListener {
               val action=homeFragmentDirections.actionHomeFragmentToDonateFragment("",request.studentUser.country,request.courseLink,request.studentUser.major,tvCoursePrice.text.toString())
               Navigation.findNavController(viewx).navigate(action)
           }
       }

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
            Log.d("Error","${e.message}")
            return 0
        }
    }

}