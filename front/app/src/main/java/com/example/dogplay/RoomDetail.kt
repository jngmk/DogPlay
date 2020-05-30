package com.example.dogplay

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.room_detail.*

class RoomDetail:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_detail)

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