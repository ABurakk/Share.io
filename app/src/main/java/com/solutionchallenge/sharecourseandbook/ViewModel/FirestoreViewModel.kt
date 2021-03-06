package com.solutionchallenge.sharecourseandbook.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionchallenge.sharecourseandbook.Model.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.Model.StandartUser
import com.solutionchallenge.sharecourseandbook.Model.StudentUser
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import kotlinx.coroutines.launch

class FirestoreViewModel(private var repository: FireStoreRepository) :ViewModel() {




    fun incrementNumberOfRequestFieldİWthEmail(mail:String)=repository.incrementNumberOfRequestFieldİWthEmail(mail)

    fun getStudentWithMail(mail: String): LiveData<StudentUser> {
        var studentLiveData = MutableLiveData<StudentUser>()
        viewModelScope.launch {
            var student = repository.getStudentsWithEmail(mail)
            studentLiveData.postValue(student)
        }

        return studentLiveData
    }

    suspend fun saveStudentToCollection(mail: String, studentUser: StudentUser) =
        repository.saveStudentToCollection(mail, studentUser)

    suspend fun saveNormalUserToCollection(mail: String, standartUser: StandartUser) =
        repository.saveNormalUsertToCollection(mail, standartUser)

    fun getUserWithMail(mail: String): LiveData<StandartUser> {
        var standartUser = MutableLiveData<StandartUser>()
        viewModelScope.launch {
            var user = repository.getUserWithMail(mail)
            standartUser.postValue(user)
        }

        return standartUser
    }

    fun makeRequest(request: OnlineCourseRequest) {
       viewModelScope.launch{
           repository.makeRequest(request)
       }
    }

    }


