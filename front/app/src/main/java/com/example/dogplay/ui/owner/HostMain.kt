package com.example.dogplay.ui.owner

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogplay.R
import com.google.common.reflect.Reflection.getPackageName
import kotlinx.android.synthetic.main.host_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest


class HostMain : Fragment(){


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val hotelnumber = "892-11-00104"
        var int_id = 0
        var hotelDetailData:HashMap<String,Any> = hashMapOf()
        var roomDetailData: Array<HashMap<String, Any>>

        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)

        server.searchHotelDetail(hotelnumber).enqueue(object: Callback<HotelDTO> {
            override fun onFailure(call: Call<HotelDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }
            override fun onResponse(
                call: Call<HotelDTO>, response: Response<HotelDTO>
            ) {
                val data: HotelDTO = response.body()!!
                hotelDetailData = data!!.data
                val str_hotelname = hotelDetailData["hotelname"].toString()
                tv_hotel_name.text = str_hotelname.toString()
            }
        })

        server.searchRoomDetail(hotelnumber).enqueue(object : Callback<RoomDTO> {
            override fun onFailure(call: Call<RoomDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }
            override fun onResponse(call: Call<RoomDTO>, response: Response<RoomDTO>) {
                val data:RoomDTO = response.body()!!
                roomDetailData = data!!.data

                var roomList = arrayListOf<MainRooms>()
                for (room in roomDetailData) {
                    Log.d("success", room.toString())
                    roomList.add(MainRooms(R.drawable.dog2, room["roomname"].toString(), room["count"].toString().split(".")[0].toInt(), 2))
                }
                println("${roomList[0].toString()} 테스트")
                rv_main.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rv_main.setHasFixedSize(true)
                rv_main.adapter = HostMainAdapter(roomList)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.host_main, container, false)
    }
}