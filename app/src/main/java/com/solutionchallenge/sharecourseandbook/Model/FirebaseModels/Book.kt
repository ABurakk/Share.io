package com.solutionchallenge.sharecourseandbook.Model.FirebaseModels

data class Book(
    var author_name:String,
    var book_kind:String,
    var currency:String,
    var image_url:String,
    var name:String,
    var price:Int
) {
    constructor():this("","","","","",0)
}