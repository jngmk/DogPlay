package com.example.dogplay.ui.owner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.dogplay.R
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.edit_hotel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EditHotel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_hotel)
        var hotelDetailData:HashMap<String,Any> = hashMapOf()
        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)

        server.searchHotelDetail("892-11-00104").enqueue(object : Callback<HotelDTO> {

            override fun onFailure(call: Call<HotelDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }

            override fun onResponse(call: Call<HotelDTO>, response: Response<HotelDTO>
            ) {
//                "latitude": 37.513524,
//                "longitude": 127.039776,
                Log.d("sucess", "성공-----------------------------------")
                Log.d("hotelDetail", response?.body().toString())
                val data:HotelDTO = response.body()!!
                hotelDetailData = data!!.data
                val str_latitude = hotelDetailData["latitude"].toString()
                val str_longitude = hotelDetailData["longitude"].toString()
                val str_userid = hotelDetailData["userid"].toString()
//                val array_detail = hotelDetailData["detail"]
                val str_hotelname = hotelDetailData["hotelname"].toString()
                et_hotel_name.setText(str_hotelname)
                val str_hotelnumber = hotelDetailData["hotelnumber"].toString()
                et_hotel_number.setText(str_hotelnumber)
                val str_address = hotelDetailData["address"].toString()
                et_hotel_address.setText(str_address)
                val str_contact = hotelDetailData["contact"].toString()
                et_hotel_contact.setText(str_contact)
                val str_info = hotelDetailData["info"].toString()
                et_hotel_info.setText(str_info)
            }

        })


    }
}
