package com.example.dogplay.ui.owner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dogplay.R
import kotlinx.android.synthetic.main.host_re_review.*
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

        val editResponseData: UpdateResponseDTO = UpdateResponseDTO()
        var responseData:HashMap<String,Any> = hashMapOf()
        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)
        var reviewId = 0
        var username = ""
        var star = 0
        var review = ""
        var time = ""
        var responseId = 0
        if (intent.hasExtra("id")) {
            reviewId = intent.getIntExtra("id", 0)
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

        tv_host_rereview_name.text = "$username 님"
        tv_host_rereview_content.text = review
        tv_host_rereview_star.text = star.toString()
        tv_host_rereview_time.text = time

        server.responseByReviewId(reviewId).enqueue(object: Callback<ResponseDTO> {
            override fun onFailure(call: Call<ResponseDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }

            override fun onResponse(call: Call<ResponseDTO>, response: Response<ResponseDTO>) {
                val data:ResponseDTO = response.body()!!
                responseData = data!!.data
                if (responseData == null) {
                    Log.d("faile", "null -------------------------------")
                } else {
                    val hostResponse = responseData["content"].toString()
                    responseId = responseData["id"].toString().split('.')[0].toInt()
                    btn_rereview.text = "수정하기"
                    tv_response_title.text = "답글 수정하기"
                    et_rereview.setText(hostResponse)
                }
            }

        })




        btn_rereview.setOnClickListener {
            var resultText = et_rereview.text.toString()
            if (responseData == null) {
                // 답글달기
                server.reReview(reviewId, username, resultText, 0).enqueue(object: Callback<RereviewDTO> {
                    override fun onFailure(call: Call<RereviewDTO>, t: Throwable) {
                        Log.d("faile", t.toString())
                        Log.d("faile", "실패-----------------------------------")
                    }
                    override fun onResponse(call: Call<RereviewDTO>, response: Response<RereviewDTO>) {
                        Log.d("success", response.toString())
                        Log.d("success", "성공-----------------------------------")
                    }
                })
            } else {
                // 답글 수정하기
                editResponseData.apply {
                    content = et_rereview.text.toString()
                    heart = 0
                    id = responseId
                    reviewid = reviewId
                    userid = username
                }
                server.updateResponse(editResponseData).enqueue(object: Callback<ReturnData> {
                    override fun onFailure(call: Call<ReturnData>, t: Throwable) {
                        Log.d("faile", t.toString())
                        Log.d("faile", "실패-----------------------------------")
                    }

                    override fun onResponse(call: Call<ReturnData>, response: Response<ReturnData>) {
                        Log.d("faile", response.toString())
                        Log.d("faile", "성공-----------------------------------")
                        val intent = Intent(this@HostReReview, HostReviewPage::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
            }

        }
    }
}