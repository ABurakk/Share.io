package com.solutionchallenge.sharecourseandbook.View.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.solutionchallenge.sharecourseandbook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class splashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        var auth=FirebaseAuth.getInstance()
        var intent1=Intent(this,MainActivity::class.java)
        var intent2=Intent(this,authActivity::class.java)
        if(auth==null){
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                startActivity(intent2)
            }
        }else{
            CoroutineScope(Dispatchers.Main).launch {
                delay(1000)
                startActivity(intent1)
            }

        }


    }
}