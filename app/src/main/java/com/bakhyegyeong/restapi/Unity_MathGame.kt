package com.bakhyegyeong.restapi

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class Unity_MathGame : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mathgame)

        val web: WebView = findViewById(R.id.math_game_view)
        val url : String = "http://43.200.84.39:8000/unity_math"


        web.webChromeClient = WebChromeClient()     //크롬으로!
        web.webViewClient = WebViewClient()         //현재 창에서! 자바에서는 따로 함수 만들었어야했는데 코틀린에서는 그냥~

        val webSettings = web.settings
        webSettings.javaScriptEnabled = true        //자바스크립트 활성화!
        //webSettings.domStorageEnabled = true      //쿠키같은 local 저장소
        web.loadUrl(url)

    }
}