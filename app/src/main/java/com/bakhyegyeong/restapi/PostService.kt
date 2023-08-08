package com.bakhyegyeong.restapi

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface PostService {

    @POST("api/question/create")
    fun requestPost(
        //header에 token정보를 같이 보내서 로그인여부를 확인!
        @Header("Authorization") authorization : String,
        @Body postData:postQuestion) : Call<ResponseBody> // output 정의

    @POST("api/user/create")
    fun requestPostUser(@Body postData : postUser) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("/api/user/login")
    fun requestUserLogin(@Field("username") username : String, @Field("password") password : String) : Call<JsonObject>
}