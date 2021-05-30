package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.authActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_in_fragment.*

class signInFragment :Fragment(R.layout.sign_in_fragment) {

    lateinit var  intentx: Intent
    lateinit var auth: FirebaseAuth
    lateinit var viewModel:FirestoreViewModel
    lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=(activity as authActivity).auth
        viewModel=(activity as authActivity).viewModel
        intentx=(activity as authActivity).intentx
        sharedPreferences=(activity as authActivity).sharedPreferences


        btnSignInSignIn.setOnClickListener {
            var mail=etMailSingIn.text.toString()
            var password=etPasswordSignIn.text.toString()

            if(mail.isEmpty()||password.isEmpty()){
                Toast.makeText(this.context,"Please Fill In The Blanks",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(mail,password).addOnSuccessListener {
                    Toast.makeText(activity!!.applicationContext,"You have succecfully sign in",Toast.LENGTH_SHORT).show()
                    startActivity(intentx)
                }.addOnFailureListener {
                    Toast.makeText(activity!!.applicationContext,"${it.message}",Toast.LENGTH_SHORT).show()

                }
            }

        }
        btnGoToCreateStudentAccount.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment)

        }
        btnGoToCreateNormalAccount.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpNormalFragment)
        }


    }





}


