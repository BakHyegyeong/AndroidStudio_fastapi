package com.bakhyegyeong.restapi

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.bakhyegyeong.restapi.GlobalVariable


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.200.84.39:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val postService : PostService = retrofit.create(PostService::class.java)

        val username : EditText = findViewById(R.id.name)
        val password : EditText = findViewById(R.id.password)
        val login_button : Button = findViewById(R.id.login_button)

        val dialog = AlertDialog.Builder(this@Login)

        login_button.setOnClickListener {
            postService.requestUserLogin(username = username.text.toString(), password = password.text.toString())
                .enqueue(object : Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if(response.isSuccessful){

                            val result = response.body()

                            if(result != null) {
                                val globalVariable = getApplication() as GlobalVariable

                                globalVariable.access_token = result.get("access_token").asString
                                globalVariable.username = result.get("username").asString
                                dialog.setMessage(globalVariable.access_token)
                                dialog.show()
                            }

                        }else {
                            dialog.setMessage("로그인 실패!")
                            dialog.show()
                        }

                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("test_debug", t.message!!)

                        dialog.setMessage("통신실패!")
                        dialog.show()
                    }
                })
        }
    }
}