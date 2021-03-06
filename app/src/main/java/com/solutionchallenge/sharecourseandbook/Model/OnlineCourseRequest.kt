package com.solutionchallenge.sharecourseandbook.Model


data class OnlineCourseRequest(
    var userMail:String,
    var courseName:String,
    var courseLink:String,
    var price:Double,
    var currency:String,
    var platformName:String,
    var studentUser: StudentUser,
    var verify:Boolean=false
){
    constructor() : this("","","",0.0,"","",StudentUser(),false)
}