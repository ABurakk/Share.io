package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.Activity.MainActivity
import com.solutionchallenge.sharecourseandbook.ViewModel.FirestoreViewModel
import com.google.firebase.auth.FirebaseAuth
import com.labters.lottiealertdialoglibrary.ClickListener
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.labters.lottiealertdialoglibrary.LottieAlertDialog
import kotlinx.android.synthetic.main.request_fragment.*

class requestFragment :Fragment(R.layout.request_fragment) {

    lateinit var auth:FirebaseAuth
    lateinit var viewModel: FirestoreViewModel
    lateinit var intentx: Intent
    lateinit var sharedPreferences: SharedPreferences
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=(activity as MainActivity).auth
        viewModel=(activity as MainActivity).viewModel
        intentx=(activity as MainActivity).intentx




        if(!auth.currentUser?.email!!.isStudent()){
            ifUserNotStudent()
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





    fun ifUserNotStudent(){
        var alertDialog : LottieAlertDialog
        alertDialog= LottieAlertDialog.Builder(requireContext(), DialogTypes.TYPE_WARNING)
            .setTitle("You should register with student account for this section")
            .setDescription("You are in charitable account you can only donate Online Course")
            .setPositiveText("Donate right now")
            .setPositiveListener(object  : ClickListener{
                override fun onClick(dialog: LottieAlertDialog) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_requestFragment_to_homeFragment2)
                }

            }).build()
        alertDialog.show()
    }
    fun ifStudentMailNotVerified(context: Context?,view: View){
        var dialog=AlertDialog.Builder(context).
        setTitle("You should verify you email adress").
        setMessage("You should verify youor acoount").
        setIcon(R.drawable.ic_waring).
        setPositiveButton("Yes"){_,_->
            Navigation.findNavController(view).navigate(R.id.action_requestFragment_to_profileFragment)

        }.setCancelable(false).create()

        dialog.show()
    }
    fun String.isStudent():Boolean{
        if(this.contains(".edu"))
            return true

        return false
    }
}