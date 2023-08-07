package com.bakhyegyeong.restapi


import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetService {
    @GET("api/question/list")
    fun requestQuestionList(@Query("page") page : Int, @Query("size") size : Int ) : Call<JsonObject>
}