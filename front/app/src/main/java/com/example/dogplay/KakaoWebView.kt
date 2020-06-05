package com.example.dogplay

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.kakao_web_view.*

class KakaoWebView :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kakao_web_view)
        val url = intent.extras!!.getString("url")
        kakaoWebView.loadUrl(url)
    }
}