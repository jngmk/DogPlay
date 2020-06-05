package com.example.dogplay

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.kakao_web_view.*

class KakaoWebView :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("이거 두번 켜지면 되는거?", "맞지 맞지")
        setContentView(R.layout.kakao_web_view)
        val url = intent.extras!!.getString("url")
        kakaoWebView.loadUrl(url)
    }
}