package com.bakhyegyeong.restapi

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class Unity_MathGame : ComponentActivity() {

    // lateinit : 나중에 초기화할 것이다. -> 나중에 값을 할당할테니 안심해라~
    private lateinit var globalVariable: GlobalVariable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mathgame)

        globalVariable = getApplication() as GlobalVariable

        val web: WebView = findViewById(R.id.math_game_view)
        val url : String = globalVariable.api_url + "unity_math"


        web.webChromeClient = WebChromeClient()     //크롬으로!
        web.webViewClient = WebViewClient()         //현재 창에서! 자바에서는 따로 함수 만들었어야했는데 코틀린에서는 그냥~

        val webSettings = web.settings
        webSettings.javaScriptEnabled = true        //자바스크립트 활성화!
        webSettings.domStorageEnabled = true      //쿠키같은 local 저장소
        web.loadUrl(url)

    }
}