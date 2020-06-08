package com.example.dogplay

import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.kakao.usermgmt.callback.LogoutResponseCallback
import com.kakao.usermgmt.UserManagement
import com.example.dogplay.ui.owner.LoginActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dogplay.ui.owner.EditService
import com.example.dogplay.ui.owner.UserInfoDTO
import kotlinx.android.synthetic.main.whole_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(){
    private lateinit var userId: String
    private lateinit var admin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whole_page)

        MutableSupplier.user.postValue(User())
        Supplier.user = User()

        admin = "0"
        userId = loadData()!!
        var userInfo:HashMap<String,Any> = hashMapOf()


//        var retrofit = Retrofit.Builder()
//            .baseUrl("http://k02a4021.p.ssafy.io:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

        getUserData()

        getDogsData()
    }

    private fun getUserData() {
        val server = API.server()
        server!!.getUserByUserId(userId).enqueue(object : Callback<UserDTO> {
            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }

            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                val user = response.body()!!.data
                if (user.phone == null) {
                    user.phone = ""
                }
                if (user.picture == null) {
                    user.picture = ""
                }
                MutableSupplier.user.postValue(user)
                Supplier.user = user
                Supplier.UserId = user.userid
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
                    tabLayout.getTabAt(3)?.setIcon(R.drawable.tab_star)
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
    private fun getDogsData() {
        val server = API.server()
        server!!.getDogByUserId(userId).enqueue(object: Callback<DogInfoDTO> {
            override fun onFailure(call: Call<DogInfoDTO>, t: Throwable) {
                Log.d("fail", t.toString())
            }

            override fun onResponse(call: Call<DogInfoDTO>, response: Response<DogInfoDTO>) {
                val dogs = response.body()!!.data
                if (dogs.size > 0) {
                    Supplier.dogs = dogs
                    MutableSupplier.dogs.postValue(dogs)
                }
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

     private fun loadData(): String? {
        val pref = getSharedPreferences("pref", 0)
        val userId = pref.getString("name", "")!!
        return userId
    }

    var oneMore:Long = -1500
    override fun onBackPressed() {
        if (System.currentTimeMillis()-oneMore <= 1500)
            finish()
        oneMore = System.currentTimeMillis()
        Toast.makeText(this, "이전 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
    }
}
