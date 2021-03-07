package com.solutionchallenge.sharecourseandbook.RemoteApi

import com.solutionchallenge.sharecourseandbook.Model.UdemyModels.UdemyCourse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CourseApi {

    @Headers("Authorization:Basic WU5qMlQxVDN1ek5BdkM4TTFDMW9jMnlHb1BtTXZoRlFKbGo0aTRpbjpyVmt5Q3g2U1BSVnhPV1UzQXZVWnd6QXdYNHpmR0lxUFZsa1U5alFFUk10ZkYxd3pyMDJ2RmFFeDF3TXhvSW5kNkFaellpanFVUFBKSllncklWSmNHb2h2UXpvZGN0QUNxTjA2VVJ1ckZSS2pBQkNhWnNKbmt0MTdHTGFmWGVtUw==")
    @GET("courses/{id}/")
    suspend fun getCourse(
        @Path("id")
        id:Int
    ): Response<UdemyCourse>
}