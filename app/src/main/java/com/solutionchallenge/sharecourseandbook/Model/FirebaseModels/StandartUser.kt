package com.solutionchallenge.sharecourseandbook.Model.FirebaseModels

data class StandartUser(
    var email:String="",
    var first_name:String="",
    var last_name:String="",
    var numberOfSharedCourse:Int=0,
    var shareCredit:Double=0.0
)