package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.solutionchallenge.sharecourseandbook.Model.StudentUser
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.authActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_up_student_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class signUpStudentFragment :Fragment(R.layout.sign_up_student_fragment) {

    lateinit var auth: FirebaseAuth
    lateinit var viewModel:FirestoreViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


         auth=(activity as authActivity).auth
         viewModel=(activity as authActivity).viewModel

        var country="Turkey"

        countrySpinner.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
               country="Turkey"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                country=parent?.getItemAtPosition(position).toString()
            }

        }
        btnRegister.setOnClickListener {
            var name=etFirstName.text.toString()
            var country=country
            var lastName=etLastName.text.toString()
            var school=etSchool.text.toString()
            var major=etMajor.text.toString()
            var mail=etMail.text.toString()
            var password=etPassword.text.toString()
            var studentUser=StudentUser(email = mail,first_name = name,last_name = lastName,school = school,major = major,country = country)

            if(name.isEmpty()||lastName.isEmpty()||school.isEmpty()||major.isEmpty()||mail.isEmpty()||password.isEmpty()){
                Toast.makeText(activity!!.applicationContext,"Please Fill all blank",Toast.LENGTH_SHORT).show()
            }
            else if(!mail.isValid()){
                Toast.makeText(activity!!.applicationContext,"Please use student maill adresss",Toast.LENGTH_SHORT).show()
            }
            else if(mail.isValid()){
                auth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener {
                    Toast.makeText(activity!!.applicationContext,"You have succefully registerd,Please don't forget verify yout email",Toast.LENGTH_LONG).show()
                    saveStudentToCollection(mail,studentUser)
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment)
                }.addOnFailureListener{
                     Toast.makeText(activity!!.applicationContext,"${it.message}",Toast.LENGTH_SHORT).show()
                }


            }


        }

        btnGoToSignIn.setOnClickListener {
             Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        btnBackToMainAuth.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment)

        }

    }


    fun saveStudentToCollection(mail:String,studentUser: StudentUser)= CoroutineScope(Dispatchers.IO).launch {
        viewModel.saveStudentToCollection(mail,studentUser)
    }

    fun String.isValid():Boolean{
        if(this.contains(".edu"))
            return true
        return false
    }




}