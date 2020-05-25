package com.example.dogplay

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

class ReviewCarouselAdapter(private val context: Context):PagerAdapter(){
    val pf = arrayOf(
        R.drawable.dog,
        R.drawable.dog2,
        R.drawable.dog3
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===`object`
    }

    override fun getCount(): Int {
        return pf.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.review_list,null)
        val profileImg = view.findViewById<View>(R.id.profilePic) as ImageView
        profileImg.setImageResource(pf[position])
        val cell = view.findViewById<View>(R.id.crsc)
        cell.clipToOutline = true
        val drawable = context.getDrawable(R.drawable.rounded)
        profileImg.setBackground(drawable)
        profileImg.setClipToOutline(true)
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