package com.example.dogplay.ui.owner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dogplay.R
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.login_main.*

class LoginMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_main)


        tvNickname.text = intent.getStringExtra("name")
        tvProfile.text = intent.getStringExtra("profile")
        btn_logout.setOnClickListener {
            UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
                override fun onCompleteLogout() {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)

                }
            })
        }
    }
//        Glide.with(this)
//            .load(intent.getStringExtra("profile"))
//            .into(imgProfile)

}
