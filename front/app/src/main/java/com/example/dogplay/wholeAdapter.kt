package com.example.dogplay

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.dogplay.ui.owner.OwnerMainFragment

class wholeAdapter(fm:FragmentManager):FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        return when (position){
            0-> searchPage()

            1-> mapPage()

            2-> chatPage()

            else -> searchPage()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}