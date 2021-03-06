package com.solutionchallenge.sharecourseandbook.View.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.lifecycle.ViewModelProvider
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.solutionchallenge.sharecourseandbook.ViewModel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class authActivity : AppCompatActivity() {

    lateinit var intentx:Intent
    lateinit var auth: FirebaseAuth
    lateinit var viewModel: FirestoreViewModel
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        intentx=Intent(this,MainActivity::class.java)
        auth= FirebaseAuth.getInstance()
        sharedPreferences=getSharedPreferences("user",Context.MODE_PRIVATE)

        var navHost2=navHost2.view!!.layoutParams

        var metric = DisplayMetrics()
        navHost2.width=metric.widthPixels
        navHost2.height=metric.heightPixels

        var repository= FireStoreRepository()
        var factory= ViewModelFactory(repository)

        if(auth.currentUser!=null){
            startActivity(intentx)
        }
        var intent=Intent()
        viewModel= ViewModelProvider(this,factory).get(FirestoreViewModel::class.java)







    }
}
