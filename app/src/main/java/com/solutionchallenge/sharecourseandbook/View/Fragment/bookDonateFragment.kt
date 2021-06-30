package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StudentUser
import com.solutionchallenge.sharecourseandbook.R
import kotlinx.android.synthetic.main.book_donate_fragment.*
import kotlinx.android.synthetic.main.book_donate_rv_view.*
import kotlinx.android.synthetic.main.book_donate_rv_view.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class bookDonateFragment : Fragment(R.layout.book_donate_fragment) {


   val args:bookDonateFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        tvBookNameDonate.text=args.BookName
        tvAuthorBook.text=args.BookAuthor
        Glide.with(view).load(args.BookImageUrl).into(bookImageDonate)


        GlobalScope.launch {
            var document=Firebase.firestore.collection("student").document(args.Owner).get().await()

            var student=document.toObject<StudentUser>()

            tvMajorD2.text=student?.major+" Student"+" from ${student?.country}"


        }




    }

}