package com.example.dogplay

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.kakao_web_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class KakaoWebView :AppCompatActivity(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val server = server()
        setContentView(R.layout.kakao_web_view)
        val sign = 1
        val webSettings = kakaoWebView.settings
        val url = intent.extras!!.getString("url")
        webSettings.javaScriptEnabled = true
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(intent)
        kakaoWebView.webViewClient = object :WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url:String
            ): Boolean {
                Log.d("현재 url", url)
                if(url != null && url.startsWith("intent://")){
                    Log.d("인텐트는 들어가지", "ㅎㅎ")
                    val intent = Intent.parseUri(url,Intent.URI_INTENT_SCHEME)
                    val existPackage = packageManager.getLaunchIntentForPackage(intent.`package`!!)
                    if (existPackage != null){
                        Log.d("패키지 없어", "ㅎㅎ")
                        startActivityForResult(intent,sign)
                        return true
                    } else{
                        val kakaoIntent = Intent(Intent.ACTION_VIEW)
                        kakaoIntent.data = Uri.parse("kakaopay/pg?url="+intent.`package`!!)
                        startActivity(kakaoIntent)
                        return false
                    }
                    return true
                } else if (url.startsWith("http://")){
                    var pg_token = url.split("pg_token=")[1]
                    Log.d("Http로 왔어", pg_token)
                    server!!.insertPaid(InsertPaid("",0,0,Supplier.SelectPayForm.cid,
                    0,Supplier.SelectPayForm.partner_order_id,pg_token,Supplier.ResponseTid,Supplier.UserId)).enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            Log.d("실패", t.toString())
                        }
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            Log.d("Paid Insert 성공", response.body().toString())
                        }

                    })
                    onBackPressed()
                }
                view!!.loadUrl(url)
                return true
           }
        }
        Log.d("이거 두번 켜지면 되는거?", url)
        kakaoWebView.loadUrl(url)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("됐어", data.toString())
    }
}
