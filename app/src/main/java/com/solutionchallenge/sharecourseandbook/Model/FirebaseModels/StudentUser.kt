package com.solutionchallenge.sharecourseandbook.Model.FirebaseModels

data class StudentUser(
    var email:String="",
    var first_name:String="",
    var last_name:String="",
    var school:String="",
    var major:String="",
    var country:String="tr",
    var numberOfSharedCourse:Int=0,
    var numberOfRequest:Int=0
)