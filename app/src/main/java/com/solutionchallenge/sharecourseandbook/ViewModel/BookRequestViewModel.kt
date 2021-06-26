package com.solutionchallenge.sharecourseandbook.ViewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.Book
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import kotlinx.coroutines.launch

class BookRequestViewModel @ViewModelInject constructor(
    var repository: FireStoreRepository
) : ViewModel() {


    private var _bookList = MutableLiveData<List<Book>>()
    var bookList : LiveData<List<Book>> = _bookList



   fun getBookList(){
       viewModelScope.launch {
           _bookList.postValue(repository.getBooks())
       }
   }


}