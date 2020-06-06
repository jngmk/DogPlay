package com.example.dogplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dogplay.ui.owner.LoginActivity
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.whole_page.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whole_page)
        val fragmentAdapter = wholeAdapter(supportFragmentManager)
        viewpager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewpager)

//        로그인 시 owner인지 사용자인지에 따라서 아이콘 변경
        var host = "892-11-00104"
        if (host != "892-11-001041") {
            tabLayout.getTabAt(0)?.setIcon(R.drawable.home)
            tabLayout.getTabAt(1)?.setIcon(R.drawable.marker)
            tabLayout.getTabAt(2)?.setIcon(R.drawable.chat)
            tabLayout.getTabAt(3)?.setIcon(R.drawable.star)
            tabLayout.getTabAt(4)?.setIcon(R.drawable.profile)
        } else {
            tabLayout.getTabAt(0)?.setIcon(R.drawable.home)
            tabLayout.getTabAt(1)?.setIcon(R.drawable.toolbar_review)
            tabLayout.getTabAt(2)?.setIcon(R.drawable.chat)
            tabLayout.getTabAt(3)?.setIcon(R.drawable.toolbar_edit)
            tabLayout.getTabAt(4)?.setIcon(R.drawable.profile)
        }
    }

    fun logout() {
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        })
        finish()
    }
}
