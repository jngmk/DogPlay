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
class ReviewCarouselAdapter(private val context: Context):PagerAdapter(){
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view ===`object`
    }

    override fun getCount(): Int {
        return Supplier.SelectHotel.data.review.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var review = Supplier.SelectHotel.data.review[position]
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.review_list,null)
        val profileImg = view.findViewById<View>(R.id.profilePic) as ImageView
        profileImg.setImageResource(R.drawable.dog)
        val cell = view.findViewById<View>(R.id.crsc)
        val profilText = view.findViewById<View>(R.id.profileText) as TextView
        profilText.text = review.userid
        val reviewContent = view.findViewById<TextView>(R.id.reviewContent)
        reviewContent.text = review.content
        val reviewAt = view.findViewById<TextView>(R.id.reviewAt)
        val dateAt = review.created
        reviewAt.text = "${dateAt[0]}-${dateAt[1]}-${dateAt[2]}-${dateAt[3]}:${dateAt[4]}"
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