package com.bakhyegyeong.restapi

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostService {

    @POST("api/question/create")
    fun requestPost(@Body postData:postQuestion) : Call<postQuestion> // output 정의
}