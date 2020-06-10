package com.example.dogplay

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_search_address.*

class SearchAddress : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var address: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_address)
        address = addressEditText
        initWebView()
        btnAddress.setOnClickListener {

            // webview url load. php 파일 주소
            webView.loadUrl("http://k02a4021.p.ssafy.io/daum_address.php")
        }
    }

    @SuppressLint("SetJavaScriptEnabled", "AddJavascriptInterface", "JavascriptInterface")
    private fun initWebView() {
        // WebView 설정
        webView = findViewById(R.id.testWebView);

        // JavaScript 허용
        webView.settings.javaScriptEnabled = true;

        // JavaScript의 window.open 허용
        webView.settings.javaScriptCanOpenWindowsAutomatically = true;

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        webView.addJavascriptInterface(AndroidBridge(), "DogPlay");

        // web client 를 chrome 으로 설정
        webView.webChromeClient = WebChromeClient();
    }

    inner class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String, arg2: String, arg3: String) {
            Handler().post {
                run() {
                    address.text = "$arg1, $arg2, $arg3"

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    initWebView();
                }
            }
        }
    }
}

