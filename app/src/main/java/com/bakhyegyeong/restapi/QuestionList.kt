package com.bakhyegyeong.restapi

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
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
        val tag :String = "all"
        val page  = 0
        val size  = 5
        val question_id = 5

        val button : Button = findViewById(R.id.list_button)
        val subject : TextView = findViewById(R.id.detail_subject)
        val content : TextView = findViewById(R.id.detail_content)
        val detail_button : Button = findViewById(R.id.detail_button)
        //val list : EditText = findViewById(R.id.board_subject)

        val dialog = AlertDialog.Builder(this@QuestionList)

        button.setOnClickListener {
            getService.requestQuestionList(tag,page, size).enqueue(object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                    if(response.isSuccessful()){
                        dialog.setTitle("알람!")
                        dialog.setMessage("정상적으로 요청되었습니다!")
                        dialog.show()

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

                    } else {
                        dialog.setMessage("목록 불러오기 실패")
                        dialog.show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("test_debug", t.message!!)

                    dialog.setTitle("알람!")
                    dialog.setMessage("통신실패!")
                    dialog.show()
                }
            })

        }


        detail_button.setOnClickListener {
            getService.requestQuestionDetail(question_id).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {



                    if(response.isSuccessful){
                        val result = response.body()

                        subject.setText(result?.get("subject")?.asString)
                        content.setText(result?.get("content")?.asString)

                        dialog.setMessage("detail을 불러왔습니다!")
                        dialog.show()
                    }else {
                        Log.d("test_debug",response.toString())

                        dialog.setMessage("detail을 불러오지 못했습니다!")
                        dialog.show()
                    }


                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                    dialog.setMessage("통신실패!")
                    dialog.show()

                }
            })
        }
    }
}