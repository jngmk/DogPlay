package com.example.dogplay

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dogplay.ui.owner.*

class wholeAdapter(fm:FragmentManager, admin: String):FragmentPagerAdapter(fm){
    val admin = admin
//        로그인 시 owner인지 사용자인지에 따라서 아이콘 변경
    override fun getItem(position: Int): Fragment {
        return if (admin == "0") {
            when (position) {
                0 -> searchPage()
                1 -> mapPage()
                2 -> chatPage()
                4 -> UserProfile()
                else -> searchPage()
            }
        } else {
            when (position) {
                0 -> OwnerMainFragment()
                1 -> HostReviewPage()
                2 -> chatPage()
                3 -> chatPage()
                4 -> OwnerProfile()
                else -> EditPage()
            }
        }
    }

    override fun getCount(): Int {
        return 5
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }


}