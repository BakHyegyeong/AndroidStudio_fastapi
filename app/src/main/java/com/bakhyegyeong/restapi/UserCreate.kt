package com.bakhyegyeong.restapi

import android.os.Bundle
import android.app.AlertDialog
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserCreate : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_create)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.200.84.39:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postService : PostService = retrofit.create(PostService::class.java)

        val username : EditText = findViewById(R.id.username)
        val password1 : EditText = findViewById(R.id.user_password1)
        val password2 : EditText = findViewById(R.id.user_password2)
        val useremail : EditText = findViewById(R.id.email)
        val birthday : EditText = findViewById(R.id.birthday)
        val button : Button = findViewById(R.id.cr_user_button)

        button.setOnClickListener {
            val postName = username.text.toString()
            val postPassword1 = password1.text.toString()
            val postPassword2 = password2.text.toString()
            val postEmail = useremail.text.toString()
            val postBirthday = birthday.text.toString()
            val postData = postUser(
                username = postName, password1 = postPassword1,
                password2 = postPassword2, email = postEmail,
                birthday = postBirthday
            )

            postService.requestPostUser(postData).enqueue(object : Callback<ResponseBody>{
                override fun onResponse( call: Call<ResponseBody>, response: Response<ResponseBody>, ) {
                    Log.d("test_debug", response.toString())

                    val dialog = AlertDialog.Builder(this@UserCreate)

                    if(response.isSuccessful()){
                        dialog.setTitle("알람!")
                        dialog.setMessage("정상적으로 등록되었습니다!")
                        dialog.show()
                    } else {

                        dialog.setMessage("회원가입 실패!")
                        dialog.show()

                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("test_debug", t.message!!)

                    val dialog = AlertDialog.Builder(this@UserCreate)
                    dialog.setTitle("알람!")
                    dialog.setMessage("통신실패!")
                    dialog.show()
                }
            })
        }
    }



}
