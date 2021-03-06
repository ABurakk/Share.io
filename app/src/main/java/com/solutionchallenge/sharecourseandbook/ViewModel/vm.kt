package com.solutionchallenge.sharecourseandbook.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StandartUser
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StudentUser
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.SuccesfulDonate
import com.solutionchallenge.sharecourseandbook.Repository.FireStoreRepository
import kotlinx.coroutines.launch

class vm(private var repository: FireStoreRepository) :ViewModel() {





    fun incrementNumberOfRequestFieldİWthEmail(mail:String)=repository.incrementNumberOfRequestFieldİWthEmail(mail)
    fun getStudentWithMail(mail: String): LiveData<StudentUser> {
        var studentLiveData = MutableLiveData<StudentUser>()
        viewModelScope.launch {
            var student = repository.getStudentsWithEmail(mail)
            studentLiveData.postValue(student)
        }

        return studentLiveData
    }

    suspend fun saveStudentToCollection(context: Context,mail: String, studentUser: StudentUser) =
        repository.saveStudentToCollection(context,mail, studentUser)

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


    fun saveSuccesfulDonateForStudent(donate: SuccesfulDonate, mail:String, price:Double){
        viewModelScope.launch {
            repository.saveSuccesfulDonateForStudentAccount(donate,mail,price)
        }
    }
    fun saveSuccesfulDonateForNormalUser(donate: SuccesfulDonate,mail: String,price: Double){
        viewModelScope.launch {
            repository.saveSuccesfulDonateForNormalAccount(donate,mail,price)
        }
    }

    fun deleteRequest(request: OnlineCourseRequest){
        viewModelScope.launch {
            repository.deleteRequest(request)
        }
    }




}


