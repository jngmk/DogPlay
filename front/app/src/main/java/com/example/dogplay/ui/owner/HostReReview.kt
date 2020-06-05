package com.example.dogplay.ui.owner

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dogplay.R
import kotlinx.android.synthetic.main.host_re_review.*
import kotlinx.android.synthetic.main.host_review_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HostReReview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_re_review)
        var hotelDetailData:HashMap<String,Any> = hashMapOf()
        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)
        var id = 0
        var username = ""
        var star = 0
        var review = ""
        var time = ""
        if (intent.hasExtra("id")) {
            id = intent.getIntExtra("id", 0)
        }
        if (intent.hasExtra("username")) {
            username = intent.getStringExtra("username").toString()
        }
        if (intent.hasExtra("star")) {
            star = intent.getIntExtra("star", 0)
        }
        if (intent.hasExtra("review")) {
            review = intent.getStringExtra("review").toString()
        }
        if (intent.hasExtra("time")) {
            time = intent.getStringExtra("time").toString()
        }

        tv_host_rereview_name.text = username
        tv_host_rereview_content.text = review
        tv_host_rereview_star.text = star.toString()
        tv_host_rereview_time.text = time

        btn_rereview.setOnClickListener {
            var resultText = et_rereview.text.toString()
            server.reReview(id, username, resultText, 0).enqueue(object: Callback<RereviewDTO> {
                override fun onFailure(call: Call<RereviewDTO>, t: Throwable) {
                    Log.d("faile", t.toString())
                    Log.d("faile", "실패-----------------------------------")
                }
                override fun onResponse(call: Call<RereviewDTO>, response: Response<RereviewDTO>) {
                    Log.d("success", response.toString())
                    Log.d("success", "성공-----------------------------------")
                }
            })
        }
    }
}