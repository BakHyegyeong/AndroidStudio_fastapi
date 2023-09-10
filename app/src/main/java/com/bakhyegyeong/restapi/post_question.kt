package com.bakhyegyeong.restapi

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

// DTO 모델 생성
data class postQuestion(
    @SerializedName("subject")
    val subject : String,

    @SerializedName("content")
    val content : String,

    @SerializedName("tag")
    val tag : String
)

// 한 DTO 모델 안에 여러 class 가능함, 파일명을 QuestionDto해서 다 여기다가 담는게 좋을듯..
data class postQ(
    @SerializedName("su")
    val subject : String,
)
