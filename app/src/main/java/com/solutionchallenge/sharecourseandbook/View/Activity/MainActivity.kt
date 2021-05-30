package com.solutionchallenge.sharecourseandbook.View.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.solutionchallenge.sharecourseandbook.ViewModel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    lateinit var intentx:Intent
    lateinit var viewModel: FirestoreViewModel
    lateinit var auth: FirebaseAuth
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentx=Intent(this,authActivity::class.java)
        auth=FirebaseAuth.getInstance()

        sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE)

        var repository=FireStoreRepository()
        var factory=ViewModelFactory(repository)
        viewModel=ViewModelProvider(this,factory).get(FirestoreViewModel::class.java)
        var fragment=navHost.view!!.layoutParams



        var metric = DisplayMetrics()
        fragment.width=metric.widthPixels
        fragment.height=metric.heightPixels


        val colorDrawable = ColorDrawable(Color.parseColor("#FFFFFF"))
        bottomNavigationView.setBackgroundColor(colorDrawable.color)

        bottomNavigationView.setupWithNavController(navHost.findNavController())


    }

}
