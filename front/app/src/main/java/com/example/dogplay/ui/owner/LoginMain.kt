package com.example.dogplay.ui.owner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dogplay.R
import kotlinx.android.synthetic.main.login_main.*

class LoginMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)

        tvNickname.text = intent.getStringExtra("name")
        tvProfile.text = intent.getStringExtra("profile")

        Glide.with(this)
            .load(intent.getStringExtra("profile"))
            .into(imgProfile)

    }
}