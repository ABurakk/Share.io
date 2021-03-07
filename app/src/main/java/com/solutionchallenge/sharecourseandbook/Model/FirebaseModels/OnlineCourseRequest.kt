package com.solutionchallenge.sharecourseandbook.Model.FirebaseModels


data class OnlineCourseRequest(
    var userMail:String,
    var courseLink:String,
    var studentUser: StudentUser,
    var verify:Boolean=false
){
    constructor() : this("","",StudentUser())
}