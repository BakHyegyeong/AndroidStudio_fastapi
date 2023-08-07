package com.bakhyegyeong.restapi

import android.provider.ContactsContract.CommonDataKinds.Email
import com.google.gson.annotations.SerializedName

data class postUser(
    @SerializedName("username")
    val username : String,

    @SerializedName("password1")
    val password1 : String,

    @SerializedName("password2")
    val password2 : String,

    @SerializedName("email")
    val email: String,

    @SerializedName("birthday")
    val birthday : String
)