package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.profile_fragment.*

class profileFragment :Fragment(R.layout.profile_fragment) {


    lateinit var intentx: Intent
    lateinit var auth: FirebaseAuth
    lateinit var viewModel:FirestoreViewModel
    lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth=(activity as MainActivity).auth
        viewModel=(activity as MainActivity).viewModel
        intentx=(activity as MainActivity).intentx
        sharedPreferences=(activity as MainActivity).sharedPreferences



        if(auth.currentUser==null){
            startActivity(intentx)
        }
        else{

            var mail=auth.currentUser!!.email.toString()
            if(mail.isStudent()){
                viewModel.getStudentWithMail(mail).observe(viewLifecycleOwner, Observer {
                    tvFullName.text=it.first_name+" "+it.last_name.toUpperCase()
                    tvMail.text=it.email
                    saveStudent(it.country,it.major,it.first_name,it.last_name,it.school)

                })
            }
            else{
                viewModel.getUserWithMail(mail).observe(viewLifecycleOwner, Observer {
                    tvFullName.text=it.first_name+" "+it.last_name.toUpperCase()
                    tvMail.text=it.email
                    sharedPreferences.edit().putString("firstName",it.first_name)
                    sharedPreferences.edit().putString("lastName",it.last_name)

                })
            }

        }

        btnUploadMoney.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_uploadMoneyFragment)
        }
        btnUserandPri.setOnClickListener {
            Toast.makeText(requireContext(),"User and Privacy Aggrement",Toast.LENGTH_SHORT).show()
        }
        btnAskedQuestion.setOnClickListener {
            Toast.makeText(requireContext(),"Asked Question",Toast.LENGTH_SHORT).show()
        }
        btnContackUs.setOnClickListener {
            Toast.makeText(requireContext(),"Text to ahmetburakilhan3@gmail.com",Toast.LENGTH_LONG).show()
        }
        btnExitApp.setOnClickListener {

        }

        btnVerifyAccount.setOnClickListener {
            if(auth.currentUser!!.isEmailVerified){
                Toast.makeText(requireContext(),"This mail already verified",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.currentUser!!.sendEmailVerification().addOnSuccessListener {
                    Toast.makeText(requireContext(),"Verification Link has send",Toast.LENGTH_SHORT).show()
                    auth.currentUser?.sendEmailVerification()
                    auth.signOut()
                    startActivity(intentx)
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),"Please try later",Toast.LENGTH_SHORT).show()
                }
            }

        }

        btnExit.setOnClickListener {
            sharedPreferences.edit().clear()
            auth.signOut()
            startActivity(intentx)
        }

        imageToken.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_uploadMoneyFragment)
        }



    }

     fun String.isStudent():Boolean{
         if(this.contains(".edu"))
             return true

         return false
     }
     fun saveStudent(country:String,major:String,firstName:String,lastName:String,school:String){
         sharedPreferences.edit().apply {
             putString("country",country)
             putString("major",major)
             putString("firstName",firstName)
             putString("lastName",lastName)
             putString("school",school)

         }.apply()
     }


}