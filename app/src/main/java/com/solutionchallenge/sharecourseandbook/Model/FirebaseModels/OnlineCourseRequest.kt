package com.solutionchallenge.sharecourseandbook.Model.FirebaseModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OnlineCourseRequest(
    var userMail:String,
    var courseLink:String,
    var studentUser: StudentUser,
    var verify:Boolean=false,
    var country:String
):Parcelable{
    constructor() : this("","",StudentUser(),false,"")
}