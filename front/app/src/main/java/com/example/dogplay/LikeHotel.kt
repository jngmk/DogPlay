package com.example.dogplay

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.my_review.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class LikeHotel :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.like_hotel)
        val server = server()
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        ReviewRecycler.layoutManager = layoutManager
        server!!.likeSearch(Supplier.UserId)
            .enqueue(object :retrofit2.Callback<searchLikeDTO>{
                override fun onFailure(call: Call<searchLikeDTO>, t: Throwable) {
                }
                override fun onResponse(
                    call: Call<searchLikeDTO>,
                    response: Response<searchLikeDTO>
                ) {
                    Log.d("즐겨찾기 검색", response.body().toString())
                    var hotelSet = ArrayList<Hotel>()
                    for(i in response.body()!!.data){
                        server.searchHotelStar(i.hotelnumber).enqueue(object :retrofit2.Callback<HotelDTO>{
                            override fun onFailure(call: Call<HotelDTO>, t: Throwable) {
                            }
                            override fun onResponse(
                                call: Call<HotelDTO>,
                                response: Response<HotelDTO>
                            ) {
                                hotelSet.add(response.body()!!.data)
                                val adapter = HotelAdapter(applicationContext,hotelSet)
                                ReviewRecycler.adapter = adapter
                                adapter.notifyDataSetChanged()
                            }
                        })
                    }
                }
            })

    }
}