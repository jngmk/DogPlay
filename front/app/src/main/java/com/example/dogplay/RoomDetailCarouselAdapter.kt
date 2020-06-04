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
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===`object`
    }

    override fun getCount(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.room_detail_carousel,null)
        // 이미지 찾아서 넣을 부분
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