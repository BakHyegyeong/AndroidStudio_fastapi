package com.bakhyegyeong.restapi

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.android.synthetic.main.activity_main.array_game_button
import kotlinx.android.synthetic.main.activity_main.card_game_button
import kotlinx.android.synthetic.main.activity_main.math_game_button


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {

    private lateinit var login_tf: TextView
    private lateinit var globalVariable: GlobalVariable

    private var postTag : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("http://43.200.84.39:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // interface 가져오기
        val postService : PostService = retrofit.create(PostService::class.java)

        // xml파일에서 각 요소들 호출(원래 자동으로 된다던데)
        val sub : EditText = findViewById(R.id.question_subject)
        val con : EditText = findViewById(R.id.question_content)
        val radio_g : RadioGroup  = findViewById(R.id.radio_group)
        login_tf  = findViewById(R.id.login_tf)

        // 버튼들
        val button : Button = findViewById(R.id.request_button)
        val cr_button : Button = findViewById(R.id.create_button)
        val li_button : Button = findViewById(R.id.list_button)
        val lo_button : Button = findViewById(R.id.login_button)
        val logout_button : Button = findViewById(R.id.logout_button)

        // global 변수 호출
        globalVariable = getApplication() as GlobalVariable

        cr_button.setOnClickListener {
            val intent = Intent(this@MainActivity, UserCreate::class.java)
            startActivity(intent)
        }

        if(globalVariable.access_token != ""){
            val intent = Intent(this@MainActivity, UserCreate::class.java)
            startActivity(intent)
        }

       li_button.setOnClickListener {
            val list = Intent(this@MainActivity, QuestionList::class.java)
            startActivity(list)
        }

        lo_button.setOnClickListener {
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
        }

        logout_button.setOnClickListener {
            globalVariable.access_token = ""
            globalVariable.username = ""
        }

        math_game_button.setOnClickListener {
            val intent = Intent(this@MainActivity, Unity_MathGame::class.java)
            startActivity(intent)
        }

        array_game_button.setOnClickListener{
            val intent = Intent(this@MainActivity, Unity_ArrayGame::class.java)
            startActivity(intent)
        }

        card_game_button.setOnClickListener {
            val intent = Intent(this@MainActivity, Unity_CardGame::class.java)
            startActivity(intent)
        }

        // 눌린 버튼의 Id값을 전달, 이 id값을 바탕으로 눌린 버튼이 뭔지 찾음
        radio_g.setOnCheckedChangeListener { group, checkedId ->
            val radio_button = findViewById<RadioButton>(checkedId)

            postTag = radio_button.text.toString()
        }

        // 질문 생성 api 호출
        button.setOnClickListener {

            val postSubject = sub.text.toString()
            val postContent = con.text.toString()
            val postData = postQuestion(subject = postSubject, content = postContent, tag = postTag)

            // object 에서 아이콘 없는거 - Callback <> retrofit으로 되어있는 거 - ovridemethod나오면 onresponse, onFailure!!
            // 또는 Callback<>{ 안에서 우클릭 - generate - ovridemethod

            //token 앞에 Bearer 띄어쓰기 붙여줘야함.
            postService.requestPost("Bearer "+ globalVariable.access_token,postData).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    // 통신에 성공했을 때

                    Log.d("test_debug", response.toString()) // 오류 확인
                    //서버의 응답값 (일단은 post라서 return값이 없음)
                    //val postQ = response.body() / response.toString()
                    //dialog.setMessage("subject: " + postQ?.subject + "/ content : " + postQ?.content)

                    val dialog = AlertDialog.Builder(this@MainActivity)
                    
                    //onResponse가 무조건 성공 X, 실패코드(3xx & 4xx 등)에도 호출 - isSuccesful() 확인이 필요
                    if(response.isSuccessful()){
                        dialog.setTitle("알람!")
                        dialog.setMessage("정상적으로 등록되었습니다!")
                        dialog.show()
                    } else {
                        if(response.code() == 401){
                            dialog.setMessage("로그인이 필요합니다!")
                            dialog.show()
                        }
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    //통신에 실패했을 때
                    Log.d("test_debug", t.message!!)

                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("알람!")
                    dialog.setMessage("통신실패!")
                    dialog.show()
                    Log.d("test","tlqkf")
                }

            })
        }
    }

    // 페이지가 로딩될때마다 바뀜
    override fun onResume() {
        super.onResume()
        if (globalVariable.access_token != "") {
            login_tf.text = globalVariable.username + "님 환영합니다."
        } else {
            login_tf.text = "로그인을 해주세요."
        }
    }
}
