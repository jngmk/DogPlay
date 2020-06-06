package com.example.dogplay

import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.UserManagement
import com.example.dogplay.ui.owner.LoginActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.dogplay.ui.owner.EditService
import com.example.dogplay.ui.owner.UserInfoDTO
import kotlinx.android.synthetic.main.whole_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whole_page)
        var admin = "0"
        val userId = loadData()
        var userInfo:HashMap<String,Any> = hashMapOf()


//        var retrofit = Retrofit.Builder()
//            .baseUrl("http://k02a4021.p.ssafy.io:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
        val server = API.server()
        server!!.getUserByUserId(userId.toString()).enqueue(object: Callback<UserDTO> {
            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }
            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                var user = response.body()!!.data
                if (user.phone == null) {
                    user.phone = ""
                }
                if (user.picture == null) {
                    user.picture = ""
                }
                Supplier.user.postValue(user)
                admin = user.admin.toString()
                Log.d("faile", "${admin}-----------------------------------")
                saveData()

                val fragmentAdapter = wholeAdapter(supportFragmentManager, admin)
                viewpager.adapter = fragmentAdapter
                tabLayout.setupWithViewPager(viewpager)
//        로그인 시 owner인지 사용자인지에 따라서 아이콘 변경

                if (admin == "0") {
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

            private fun saveData() {
                val pref = getSharedPreferences("pref", 0)
                val edit = pref.edit()
                edit.putString("admin", admin)
                edit.apply()
            }
        })

    }

    fun logout() {
        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
            override fun onCompleteLogout() {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        })

    }
    fun mainFinish() {
        finish()
    }

     private fun loadData(): String? {
        val pref = getSharedPreferences("pref", 0)
        val userId = pref.getString("name", "")!!
        return userId
    }

}
