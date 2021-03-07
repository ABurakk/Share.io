package com.solutionchallenge.sharecourseandbook.Model.UdemyModels

data class UdemyCourse(
    val _class: String,
    val id: Int,
    val image_125_H: String,
    val image_240x135: String,
    val image_480x270: String,
    val is_paid: Boolean,
    val is_practice_test_course: Boolean,
    val price: String,
    val price_detail: PriceDetail,
    val price_serve_tracking_id: String,
    val published_title: String,
    val title: String,
    val tracking_id: String,
    val url: String,
    val visible_instructors: List<Visibleİnstructor>
)