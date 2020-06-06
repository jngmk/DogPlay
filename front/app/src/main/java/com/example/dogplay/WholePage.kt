package com.example.dogplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.whole_page.*

class WholePage :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whole_page)

        val fragmentAdapter = wholeAdapter(supportFragmentManager, "0")
        viewpager.adapter = fragmentAdapter
    }

    private fun loadData(): String? {
        val pref = getSharedPreferences("pref", 0)
        val admin = pref.getString("admin", "")
        return admin
    }
}