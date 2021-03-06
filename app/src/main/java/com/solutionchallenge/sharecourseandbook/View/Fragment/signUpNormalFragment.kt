package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.solutionchallenge.sharecourseandbook.Model.StandartUser
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.authActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_up_normal_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class signUpNormalFragment :Fragment(R.layout.sign_up_normal_fragment) {


    lateinit var auth: FirebaseAuth
    lateinit var viewModel:FirestoreViewModel
    lateinit var intentx: Intent
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=(activity as authActivity).auth
        viewModel=(activity as authActivity).viewModel
        intentx=(activity as authActivity).intentx
        btnSignUpNormalRegister.setOnClickListener {

            var name=etSignUpNormalName.text.toString()
            var lastName=etSignUpNormalLastName.text.toString()
            var mail=etSignUpNormalMail.text.toString()
            var password=etSignUpNormalPassword.text.toString()
            var user=StandartUser(mail,name,lastName)
            auth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener {
              CoroutineScope(Dispatchers.IO).launch {
                  viewModel.saveNormalUserToCollection(mail,user)
                  withContext(Dispatchers.Main){
                      Toast.makeText(activity!!.applicationContext,"You have succesfully registered",Toast.LENGTH_SHORT).show()
                  }
                  startActivity(intentx)

              }

            }.addOnFailureListener {
                Toast.makeText(activity!!.applicationContext,"${it.message.toString()}",Toast.LENGTH_SHORT).show()
            }
        }

        btnGoToSignInNormal.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signUpNormalFragment_to_signInFragment)
        }
    }
}