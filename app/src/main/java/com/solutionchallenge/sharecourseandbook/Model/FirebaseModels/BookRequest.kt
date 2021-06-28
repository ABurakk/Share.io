package com.solutionchallenge.sharecourseandbook.Model.FirebaseModels

data class BookRequest(
    var requestOwnerMail:String,
    var book: Book
){
    constructor():this("",Book())
}