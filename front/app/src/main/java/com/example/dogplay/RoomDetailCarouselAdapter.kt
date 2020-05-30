package com.example.dogplay

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
class RoomDetailCarouselAdapter(private val context: Context):PagerAdapter(){

    val review = Supplier.HotelDetailReview

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===`object`
    }

    override fun getCount(): Int {
        return review.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.room_detail_carousel,null)
        // 이미지 찾아서 넣을 부분
//        val profileImg = view.findViewById<View>(R.id.profilePic) as ImageView
//        profileImg.setImageResource(R.drawable.dog)
//        val cell = view.findViewById<View>(R.id.crsc)
//        val profilText = view.findViewById<View>(R.id.profileText) as TextView
//        profilText.text = review[position]["userid"].toString()
//        val reviewContent = view.findViewById<TextView>(R.id.reviewContent)
//        reviewContent.text = review[position]["content"].toString()
//        val reviewAt = view.findViewById<TextView>(R.id.reviewAt)
//        val dateAt = review[position]["created"] as ArrayList<Double>
//        reviewAt.text = "${dateAt[0].toInt()}-${dateAt[1].toInt()}-${dateAt[2].toInt()}-${dateAt[3].toInt()}:${dateAt[4].toInt()}:${dateAt[5].toInt()}"
//
//        val roomDetailTitle = view.findViewById<TextView>(R.id.RoomDetailTitle)
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
        val cell = view.findViewById<View>(R.id.rdcs)
        cell.clipToOutline = true
        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.invalidate()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return super.getPageTitle(position)
    }
}