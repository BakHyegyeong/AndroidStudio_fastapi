package com.bakhyegyeong.restapi

import com.google.gson.annotations.SerializedName

// DTO 모델 생성
data class postQuestion(
    @SerializedName("subject")
    val subject : String,

    @SerializedName("content")
    val content : String
)
