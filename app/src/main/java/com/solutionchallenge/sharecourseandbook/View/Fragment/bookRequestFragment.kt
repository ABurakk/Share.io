package com.solutionchallenge.sharecourseandbook.View.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.Book
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.View.recyclerViewAdapters.bookRequestAdapter
import com.solutionchallenge.sharecourseandbook.ViewModel.BookRequestViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.book_request_fragment.*
import kotlinx.coroutines.*
import okhttp3.internal.notifyAll

@AndroidEntryPoint
class bookRequestFragment :Fragment(R.layout.book_request_fragment) {

    lateinit var viewModel: BookRequestViewModel
    lateinit var adapter: bookRequestAdapter
    lateinit var bookList: List<Book>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel=ViewModelProvider(this).get(BookRequestViewModel::class.java)


        bookList= listOf()
        viewModel.getBookList()
        adapter= bookRequestAdapter(bookList,requireView())
        rvBookList.adapter= adapter
        rvBookList.layoutManager=LinearLayoutManager(requireContext())
        BookListObserver()






    }



    fun BookListObserver(){
        viewModel.bookList.observe(viewLifecycleOwner, Observer {
            if(it.size>0){
                adapter.bookList=it
                adapter.notifyDataSetChanged()

            }
            else{
              Log.d("BookRequest List","Hata")
            }
        })
    }


}