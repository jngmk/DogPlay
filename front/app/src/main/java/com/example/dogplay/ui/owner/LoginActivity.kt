package com.example.dogplay.ui.owner

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auth0.android.jwt.JWT
import com.example.dogplay.MainActivity
import com.example.dogplay.R
import com.example.dogplay.Supplier
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    var userId = ""
    var ourToken = ""
    var retrofit = Retrofit.Builder()
        .baseUrl("http://k02a4021.p.ssafy.io:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var server = retrofit.create(EditService::class.java)

    private lateinit var callback: SessionCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppKeyHash()
        setContentView(R.layout.kakao_login)
        callback = SessionCallback()
        Session.getCurrentSession().addCallback(callback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Session.getCurrentSession().removeCallback(callback)
    }

    // 앱의 해쉬 키 얻는 함수
    private fun getAppKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            Log.e("name not found", e.toString())
        }
    }

    private inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {
            getAppKeyHash()
            val token = Session.getCurrentSession().tokenInfo.accessToken.toString()
            Log.d("test111", "${token}")


            // 로그인 세션이 열렸을 때
            UserManagement.getInstance().me( object : MeV2ResponseCallback() {
                override fun onSuccess(result: MeV2Response?) {

                    // 로그인이 성공했을 때

                    server.kakaoLogin(token).enqueue(object: Callback<TokenDTO> {
                        override fun onFailure(call: Call<TokenDTO>, t: Throwable) {
                            Log.d("faile", t.toString())
                            Log.d("faile", "실패-----------------------------------")
                        }

                        override fun onResponse(call: Call<TokenDTO>, response: Response<TokenDTO>) {
                            Log.d("token", "${token}~~~~~~~~~~~")
                            val data:TokenDTO = response.body()!!
                            val jwtData = data!!.data
                            ourToken = jwtData["token"].toString()
                            Log.d("token11221", "${ourToken}")

                            val decodeJWT = JWT(ourToken)
                            userId = decodeJWT.getClaim("userId").asString().toString()
                            Log.d("token11221", "$userId")
                            Supplier.UserId = userId

                            saveData()

                            var intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("name", userId)
                            intent.putExtra("profile", result!!.getProfileImagePath())
                            intent.putExtra("token", token)
                            startActivity(intent)
                            finish()
                        }

                        private fun saveData() {
                            val pref = getSharedPreferences("pref", 0)
                            val edit = pref.edit()
                            edit.putString("name", userId)
                            edit.apply()
                        }

                    })

                }

                override fun onSessionClosed(errorResult: ErrorResult?) {
                    // 로그인 도중 세션이 비정상적인 이유로 닫혔을 때
                    Log.d("로그인 에러", errorResult.toString())
                    Toast.makeText(
                        this@LoginActivity,
                        "다시 로그인 해주세요!",
//                        "세션이 닫혔습니다. 다시 시도해주세요 : ${errorResult.toString()}",
                        Toast.LENGTH_SHORT).show()
                }
            })
        }
        override fun onSessionOpenFailed(exception: KakaoException?) {
            // 로그인 세션이 정상적으로 열리지 않았을 때
            if (exception != null) {
                com.kakao.util.helper.log.Logger.e(exception)
                Toast.makeText(
                    this@LoginActivity,
                    "다시 로그인 해주세요!",
//                    "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요 : $exception",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun redirectSignupActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
