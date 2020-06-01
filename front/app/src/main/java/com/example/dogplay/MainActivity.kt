package com.example.dogplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.whole_page.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whole_page)
        val fragmentAdapter = wholeAdapter(supportFragmentManager)
        viewpager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewpager)
        tabLayout.getTabAt(0)?.setIcon(R.drawable.home)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.marker)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.chat)
        tabLayout.getTabAt(3)?.setIcon(R.drawable.star)
    }
}
