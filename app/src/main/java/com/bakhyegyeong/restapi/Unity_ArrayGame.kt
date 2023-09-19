package com.bakhyegyeong.restapi

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bakhyegyeong.restapi.ui.theme.RestapiTheme

class Unity_ArrayGame : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arraygame)

        val web: WebView = findViewById(R.id.array_game_view)
        val url : String = "http://43.200.84.39:8000/unity_array"


        web.webChromeClient = WebChromeClient()     //크롬으로!
        web.webViewClient = WebViewClient()         //현재 창에서! 자바에서는 따로 함수 만들었어야했는데 코틀린에서는 그냥~

        val webSettings = web.settings
        webSettings.javaScriptEnabled = true        //자바스크립트 활성화!
        webSettings.domStorageEnabled = true      //쿠키같은 local 저장소
        web.loadUrl(url)

    }
}