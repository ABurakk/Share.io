package com.solutionchallenge.sharecourseandbook.Model

data class SuccesfulDonate(
    var currentuserMail:String,
    var courseName:String,
    var courseLink:String,
    var price:Double,
    var currency:String,
    var platformName:String,
    var studentUser: StudentUser,
) {
}