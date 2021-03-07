package com.solutionchallenge.sharecourseandbook.Repository

import com.solutionchallenge.sharecourseandbook.RemoteApi.RetrofitObject

class UdemyCourseRepository {

    suspend fun getCourse(id:Int)= RetrofitObject.apiService.getCourse(id)

}