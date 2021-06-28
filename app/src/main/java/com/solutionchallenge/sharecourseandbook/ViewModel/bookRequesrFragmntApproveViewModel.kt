package com.solutionchallenge.sharecourseandbook.ViewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.Book
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import kotlinx.coroutines.launch

class bookRequesrFragmntApproveViewModel @ViewModelInject constructor(
        var repository: FireStoreRepository
    ):ViewModel() {



     fun saveBookRequest(mail:String,book:Book){
        viewModelScope.launch {
            repository.saveBookRequest(mail,book)
        }
    }

}