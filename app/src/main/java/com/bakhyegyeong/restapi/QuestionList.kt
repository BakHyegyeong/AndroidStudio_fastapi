package com.bakhyegyeong.restapi

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.200.84.39:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val getService : GetService = retrofit.create(GetService::class.java)

        //val ex : Array<String> = arrayOf("tlqkf")
        //val list :ListView = findViewById(R.id.questionList)
        val page  = 0
        val size  = 5

        val button : Button = findViewById(R.id.list_button)
        val list : EditText = findViewById(R.id.board_subject)

        button.setOnClickListener {
            getService.requestQuestionList(page, size).enqueue(object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    //response.body()?.asJsonObject?.get("question_list").toString()
                    //response의 값을 JsonObject로 바꾸고 그 안에서 total과 question_list의 값을 받아옴.
                    val result = response.body()
                    val total = result?.get("total")?.asInt
                    val list = result?.get("question_list")?.asJsonArray

                    // 배열 안의 값이 JsonElement형태로 있기 때문에 JsonObject로 다시 변환.
                    if (list != null){
                        for(item in list){
                            val question = item.asJsonObject

                            Log.d("test_debug",question.get("subject").asString)

                        }
                    }



                    val dialog = AlertDialog.Builder(this@QuestionList)

                    if(response.isSuccessful()){
                        dialog.setTitle("알람!")
                        dialog.setMessage("정상적으로 요청되었습니다!")
                        dialog.show()

                    } else {
                        dialog.setMessage("목록 불러오기 실패")
                        dialog.show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("test_debug", t.message!!)

                    val dialog = AlertDialog.Builder(this@QuestionList)
                    dialog.setTitle("알람!")
                    dialog.setMessage("통신실패!")
                    dialog.show()
                }
            })

        }
    }
}