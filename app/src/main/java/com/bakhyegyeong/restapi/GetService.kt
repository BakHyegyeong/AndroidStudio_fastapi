package com.bakhyegyeong.restapi


import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetService {
    @GET("api/question/list")
    fun requestQuestionList(@Query("tag") tag : String ,@Query("page") page : Int, @Query("size") size : Int ) : Call<JsonObject>

    //{}안에 있는 값을 Path로 정의시켜줘야 함
    @GET("api/question/detail/{question_id}")
    fun requestQuestionDetail(@Path("question_id") question_id : Int) : Call<JsonObject>
}