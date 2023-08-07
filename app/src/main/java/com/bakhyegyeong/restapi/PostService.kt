package com.bakhyegyeong.restapi

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PostService {

    @POST("api/question/create")
    fun requestPost(@Body postData:postQuestion) : Call<ResponseBody> // output 정의

    @POST("api/user/create")
    fun requestPostUser(@Body postData : postUser) : Call<ResponseBody>
}