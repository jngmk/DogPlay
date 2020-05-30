package com.example.dogplay

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.room_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomDetail:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_detail)
        var extra = intent.getStringExtra("rno")
        var HotelNo = extra.toInt()
        val server = server()
        server!!.searchRoomDetail(id = HotelNo).enqueue(object :Callback<RoomDetailDTO>{
            override fun onFailure(call: Call<RoomDetailDTO>, t: Throwable) {
                Log.d("설마", t.toString())
            }

            override fun onResponse(call: Call<RoomDetailDTO>, response: Response<RoomDetailDTO>) {
                Log.d("이걸로 룸 끝", response.body().toString())
                val roomDetailDate = response.body()!!.data
                RoomDetailTitle.text = roomDetailDate.roomname
                RoomDetailSize.text = "${roomDetailDate.minsize}Kg ~ ${roomDetailDate.maxsize}Kg"
                RoomDetailAddress.text = "${Supplier.PickAdress}"
                RoomDetailPrice.text = "${roomDetailDate.price} 원"
                RoomIntro.text = "${roomDetailDate.info}"
            }

        })


//        val RoomData = Supplier.RoomDetail[position]
//        Log.d("Roomdata", RoomData["roomname"].toString())
//        roomDetailTitle.text = RoomData["roomname"].toString()
//        val minSize = RoomData["minsize"].toString().toDouble().toInt()
//        val maxSize = RoomData["maxsize"].toString().toDouble().toInt()
//        val roomDetailSize = view.findViewById<TextView>(R.id.RoomDetailSize)
//        roomDetailSize.text = "${minSize} ~ ${maxSize}"
//        val roomDetailAddress = view.findViewById<TextView>(R.id.RoomDetailAddress)
//        roomDetailAddress.text = "몰라 안오네"
//        val roomDetailPrice = view.findViewById<TextView>(R.id.RoomDetailPrice)
//        roomDetailPrice.text = "${RoomData["price"].toString().toDouble().toInt()}"


        val vp = findViewById(R.id.RoomDetailCarousel) as ViewPager
        val RoomAdapter = RoomDetailCarouselAdapter(applicationContext)
        vp.adapter = RoomAdapter
        vp.clipToPadding = false
        RoomAdapter.notifyDataSetChanged()
        Log.d("오늘의 마지막", Supplier.RoomDetail.toString())
    }
}