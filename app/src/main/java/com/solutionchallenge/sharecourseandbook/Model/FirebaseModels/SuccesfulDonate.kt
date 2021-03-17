package com.solutionchallenge.sharecourseandbook.Model.FirebaseModels

import com.solutionchallenge.sharecourseandbook.Model.FirebaseModels.StudentUser

data class SuccesfulDonate(
    var currentuserMail:String,
    var request: OnlineCourseRequest
) {
}