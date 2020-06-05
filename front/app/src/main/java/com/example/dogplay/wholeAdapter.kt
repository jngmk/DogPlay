package com.example.dogplay

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dogplay.ui.owner.EditPage
import com.example.dogplay.ui.owner.HostMain
import com.example.dogplay.ui.owner.HostReviewPage
import com.example.dogplay.ui.owner.OwnerMainFragment

class wholeAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){

//        로그인 시 owner인지 사용자인지에 따라서 아이콘 변경
    var host = "892-11-00104"
    override fun getItem(position: Int): Fragment {
        return if (host == "892-11-001041") {
            when (position) {
                0 -> HostMain()
                1 -> HostReviewPage()
                2 -> chatPage()
                else -> EditPage()
            }
        } else {
            when (position) {
                0 -> searchPage()
                1 -> mapPage()
                2 -> chatPage()
                else -> searchPage()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}