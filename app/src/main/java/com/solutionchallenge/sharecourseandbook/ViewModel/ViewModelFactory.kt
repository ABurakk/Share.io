package com.solutionchallenge.sharecourseandbook.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository

class ViewModelFactory(
    val repository: FireStoreRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return vm(repository) as T
    }

}