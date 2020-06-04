package com.example.dogplay.ui.owner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogplay.R
import kotlinx.android.synthetic.main.host_main.*
import kotlinx.android.synthetic.main.host_review_page.*
import kotlinx.android.synthetic.main.hotel_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HostReviewPage : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val hotelnumber = "892-11-00104"
        var reservationData: Array<HashMap<String, Any>>

        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)
        server.searchReservation(hotelnumber).enqueue(object: Callback<ReservationDTO> {
            override fun onFailure(call: Call<ReservationDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }

            override fun onResponse(call: Call<ReservationDTO>, response: Response<ReservationDTO>) {
                val data: ReservationDTO = response.body()!!
                reservationData = data!!.data
                val reservationList = arrayListOf<HostReviews>()
                for (reserv in reservationData) {
                    var username = reserv["userid"].toString()
                    var star = reserv["star"].toString().split(".")[0].toInt()
                    var review = reserv["content"].toString()
                    var created = reserv["created"] as ArrayList<Double>
                    var year = created[0].toInt()
                    var month = created[1].toInt()
                    var day = created[2].toInt()
                    var hour = created[3].toInt()
                    var minute = created[4].toInt()
                    var second = created[5].toInt()
                    var time = "${year}-${month}-${day} ${hour}:${minute}:${second}"
                    reservationList.add(HostReviews(username, star, review, time))
                }
                rv_host_review.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rv_host_review.setHasFixedSize(true)
                rv_host_review.adapter = HostReviewAdapter(reservationList)

            }

        })

//        val checkReservationList = arrayListOf(
//            HostReviews("희철이", 5, "안녕하세요", "2020.6.2 - 2020.6.3"),
//            HostReviews("양동이", 5, "감사합니다.", "2020.6.4 - 2020.6.7")
//        )


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.host_review_page, container, false)
    }

}