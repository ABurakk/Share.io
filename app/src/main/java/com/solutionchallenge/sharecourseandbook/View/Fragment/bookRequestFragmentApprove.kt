package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.Book
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.ViewModel.BookRequestViewModel
import com.solutionchallenge.sharecourseandbook.ViewModel.bookRequesrFragmntApproveViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.book_request_approve_fragment.*
import kotlinx.android.synthetic.main.book_request_view.*
import kotlinx.android.synthetic.main.book_request_view.view.*

@AndroidEntryPoint
class bookRequestFragmentApprove : Fragment(R.layout.book_request_approve_fragment) {

    val args:bookRequestFragmentApproveArgs by navArgs()
    lateinit var viewModel: bookRequesrFragmntApproveViewModel
    lateinit var author:String
    lateinit var imaegUrl:String
    lateinit var bookName:String
    lateinit var bookKind:String
    var price:Int=0
    lateinit var currency:String
    lateinit var book:Book
    lateinit var auth:FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel= ViewModelProvider(this).get(bookRequesrFragmntApproveViewModel::class.java)
        auth= FirebaseAuth.getInstance()
        setUpArgs()
        tvBookAuthorApprove.text=author
        tvBookPriveApprove.text="Price: "+price.toString()+currency
        tvBookNameApprove.text=bookName
        tvBookKindApprove.text="Inside the ${bookKind}"
        Glide.with(view).load(args.imageUrl).into(imageView26)


        btnBookRequesrApprove.setOnClickListener {
            viewModel.saveBookRequest("${auth.currentUser?.email}",book)
            Toast.makeText(requireContext(),"Book Requested",Toast.LENGTH_LONG).show()
            val action=bookRequestFragmentApproveDirections.actionBookRequestFragmentApproveToProfileFragment()
            Navigation.findNavController(requireView()).navigate(action)
        }




    }



    fun setUpArgs(){
        author=args.author
        imaegUrl=args.imageUrl
        bookName=args.bookName
        price=args.price
        currency=args.currency
        bookKind=args.bookKind

        book= Book(author,bookKind,currency,imaegUrl,bookName,price)
    }
}