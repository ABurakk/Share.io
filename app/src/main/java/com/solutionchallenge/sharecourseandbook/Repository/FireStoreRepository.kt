package com.solutionchallenge.sharecourseandbook.Repository

import android.content.Context
import android.os.ParcelFileDescriptor.open
import android.util.Log
import android.widget.Toast
import com.google.common.reflect.TypeToken
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.OnlineCourseRequest
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StandartUser
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StudentUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.core.OnlineState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.SuccesfulDonate
import com.solutionchallenge.sharecourseandbook.Model.humanDevelopmentIndexItem
import com.solutionchallenge.sharecourseandbook.R
import com.solutionchallenge.sharecourseandbook.Utils.constant
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.io.InputStream
import java.nio.channels.AsynchronousSocketChannel.open


class FireStoreRepository {

    var studentCollection=Firebase.firestore.collection("student")
    var normalUserCollection=Firebase.firestore.collection("normalUser")
    var requestCollection=Firebase.firestore.collection("request")
    var succesfulDonateCollection=Firebase.firestore.collection("succesfulDonate")






    suspend fun saveSuccesfulDonateForStudentAccount(succesfulDonate: SuccesfulDonate,mail:String,coursePrice:Double){
        succesfulDonateCollection.add(succesfulDonate).await()
        studentCollection.document(mail).update("shareCredit",FieldValue.increment(-coursePrice))
    }
    suspend fun saveSuccesfulDonateForNormalAccount(succesfulDonate: SuccesfulDonate,mail:String,coursePrice:Double){
        succesfulDonateCollection.add(succesfulDonate).await()
        normalUserCollection.document(mail).update("shareCredit",FieldValue.increment(-coursePrice))
    }
    fun incrementNumberOfRequestFieldİWthEmail(mail: String){
        studentCollection.document(mail).update("numberOfRequest",FieldValue.increment(1))
    }

    fun saveStudentToCollection(context: Context,mail:String,studentUser: StudentUser){

         if(studentUser.gender=="Women")
             studentUser.priorityPoint=studentUser.priorityPoint+1

         studentUser.priorityPoint=studentUser.priorityPoint+takeRankOfHumanDevelopemntIndexGivenCountry(context,studentUser.country)

        studentCollection.document(mail).set(studentUser)
    }
    fun saveNormalUsertToCollection(mail:String,standartUser: StandartUser){
        normalUserCollection.document(mail).set(standartUser)
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
    suspend fun getUserWithMail(mail:String) : StandartUser {
        var documentSnapshot=normalUserCollection.document(mail).get().await()
        var user: StandartUser
        if(documentSnapshot!=null){
            user= documentSnapshot.toObject<StandartUser>()!!
        }
        else{
            user=StandartUser()

        }
        return user
        
    }
    suspend fun makeRequest(request: OnlineCourseRequest)=requestCollection.add(request).await()
    suspend fun deleteRequest(request: OnlineCourseRequest){

            var documentSnapshot=requestCollection.
            whereEqualTo("courseLink",request.courseLink).
            whereEqualTo("userMail",request.studentUser.email).get().await().documents[0]

            requestCollection.document(documentSnapshot.id).delete()



    }


    //Wi give prioity students who live in poor country. With this function we detirmemine this point
    private fun takeRankOfHumanDevelopemntIndexGivenCountry(context: Context,country:String):Int{

        try {
            var data=getJsonDataFromAsset(context,"data.json")
            val gson = Gson()
            val listPersonType = object : TypeToken<List<humanDevelopmentIndexItem>>() {}.type
            var countries: List<humanDevelopmentIndexItem> = gson.fromJson(data, listPersonType)
            countries.forEach {
                if(it.country==country){
                    if(it.humanDevelopmentIndex.toDouble()>0.800)
                        return 1
                    else if(it.humanDevelopmentIndex.toDouble()<0.800&&it.humanDevelopmentIndex.toDouble()>0.700)
                        return 2
                    else if(it.humanDevelopmentIndex.toDouble()<0.700&&it.humanDevelopmentIndex.toDouble()>0.550)
                        return 3
                    else
                        return 4
                }
            }

        }catch (e :Exception){
             Log.d("takeRankFunction",e.message.toString())
             return 0
        }
       return 0
    }
    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

}