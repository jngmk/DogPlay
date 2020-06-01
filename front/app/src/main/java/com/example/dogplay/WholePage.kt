package com.example.dogplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.whole_page.*

class WholePage :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whole_page)

        val fragmentAdapter = wholeAdapter(supportFragmentManager)
        viewpager.adapter = fragmentAdapter
    }
}