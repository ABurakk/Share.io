package com.solutionchallenge.sharecourseandbook.Repository

import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StandartUser
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StudentUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.SuccesfulDonate
import kotlinx.coroutines.tasks.await

class FireStoreRepository {

    var studentCollection=Firebase.firestore.collection("student")
    var normalUserCollection=Firebase.firestore.collection("normalUser")
    var requestCollection=Firebase.firestore.collection("request")



    fun incrementNumberOfRequestFieldİWthEmail(mail: String){
        studentCollection.document(mail).update("numberOfRequest",FieldValue.increment(1))
    }

    suspend fun saveStudentToCollection(mail:String,studentUser: StudentUser){
        studentCollection.document(mail).set(studentUser)
    }

    suspend fun getStudentsWithEmail(email:String): StudentUser {
        var documentSnapshot=studentCollection.document(email).get().await()
        var student: StudentUser
        if(documentSnapshot!=null)
            student= documentSnapshot.toObject<StudentUser>()!!
        else
            student=
                StudentUser()


        return student
    }
    suspend fun saveNormalUsertToCollection(mail:String,standartUser: StandartUser){
      normalUserCollection.document(mail).set(standartUser)
    }
    suspend fun getUserWithMail(mail:String) : StandartUser {
        var documentSnapshot=normalUserCollection.document(mail).get().await()
        var user: StandartUser
        if(documentSnapshot!=null){
            user= documentSnapshot.toObject<StandartUser>()!!
        }
        else{
            user=
                StandartUser()

        }
        return user
        
    }

    suspend fun makeRequest(request: OnlineCourseRequest)=requestCollection.add(request).await()



}