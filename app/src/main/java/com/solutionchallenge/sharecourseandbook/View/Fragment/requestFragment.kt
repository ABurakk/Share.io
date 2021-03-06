package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.request_fragment.*

class requestFragment :Fragment(R.layout.request_fragment) {

    lateinit var auth:FirebaseAuth
    lateinit var viewModel: FirestoreViewModel
    lateinit var intentx: Intent
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=(activity as MainActivity).auth
        viewModel=(activity as MainActivity).viewModel
        intentx=(activity as MainActivity).intentx

        if(!auth.currentUser?.email!!.isStudent()){
             ifUserNotStudent(this.context,view)
        }


        else if(!auth.currentUser!!.isEmailVerified&&auth.currentUser!!.email!!.isStudent()){
             ifStudentMailNotVerified(this.context,view)
        }

        btnRequestBook.setOnClickListener {
              Toast.makeText(this.context,"This section not available for now",Toast.LENGTH_SHORT).show()
        }

        btnRequestOnlineCourse.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_requestFragment_to_onlineCourseFragment)

        }
    }



    fun ifUserNotStudent(context: Context?, view:View){
        var dialog=AlertDialog.Builder(context).
                setTitle("You should register with student account to make a request").
                setMessage("If you are student  create a student account for yourself if not gift a course from home page").
                setIcon(R.drawable.ic_waring).
                setPositiveButton("Yes"){_,_->
                     Toast.makeText(activity!!.applicationContext,"Create a student account",Toast.LENGTH_LONG).show()
                    startActivity(intentx)
                    auth.signOut()
                }.
                 setNegativeButton("No"){_,_->
                     Toast.makeText(activity!!.applicationContext,"You can sign in after verify you email",Toast.LENGTH_LONG).show()
                     startActivity(intentx)
                     auth.signOut()

                 }.create()

        dialog.show()

    }
    fun ifStudentMailNotVerified(context: Context?,view: View){
        var dialog=AlertDialog.Builder(context).
        setTitle("You should verify you email adress").
        setMessage("You can click yes button to send a verification link").
        setIcon(R.drawable.ic_waring).
        setPositiveButton("Yes"){_,_->
            Toast.makeText(activity!!.applicationContext,"We have sent verification link you email adress",Toast.LENGTH_LONG).show()
            auth.currentUser!!.sendEmailVerification()
            Navigation.findNavController(view).navigate(R.id.action_requestFragment_to_profileFragment)
        }.
        setNegativeButton("No"){_,_->
            Navigation.findNavController(view).navigate(R.id.action_requestFragment_to_profileFragment)


        }.create()

        dialog.show()
    }
    fun String.isStudent():Boolean{
        if(this.contains(".edu"))
            return true

        return false
    }
}