package com.solutionchallenge.sharecourseandbook.RemoteApi

import com.solutionchallenge.sharecourseandbook.Utils.constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val apiService = getRetrofit().create(CourseApi::class.java)

}