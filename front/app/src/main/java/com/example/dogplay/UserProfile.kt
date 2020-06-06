package com.example.dogplay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogplay.ui.owner.LoginActivity
import com.example.dogplay.ui.owner.OwnerEnrollHotel
import com.example.dogplay.ui.owner.USER_ID
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfile.newInstance] factory method to
 * create an instance of this fragment.
 */

const val USER_ID = "com.example.dogplay.USER_ID"
const val USER_NICKNAME = "com.example.dogplay.USER_NICKNAME"
const val USER_PHONE = "com.example.dogplay.USER_PHONE"
const val USER_PICTURE = "com.example.dogplay.USER_PICTURE"
const val USER_SOCIAL = "com.example.dogplay.USER_SOCIAL"
const val USER_ADMIN = "com.example.dogplay.USER_ADMIN"

class UserProfile : Fragment() {
    private lateinit var userId: String
    private lateinit var userNickname: String
    private lateinit var userPhone: String
    private lateinit var userPicture: String
    private var userSocial = 0
    private var userAdmin = 0
    private val userSupplier = UserSupplier()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        val user = (activity as MainActivity).user
        val user = userSupplier.user
        user.apply {
            userId = this.userid
            userNickname = this.nickname
            userPhone = this.phone
            userPicture = this.picture
            userSocial = this.social
            userAdmin = this.admin
        }

        btnUserInfo.setOnClickListener {
            editUserInfo()
        }
        btnMyDogs.setOnClickListener {
            addMyDogs()
        }
        btnReviews.setOnClickListener {
            openReviewPage()
        }
        btnNotification.setOnClickListener {
            openNotificationPage()
        }
        btnEnrollMyHotel.setOnClickListener {
            enrollHotel(user.userid)
        }
        btnChangeToOwnerView.setOnClickListener {
            changeView(user.userid)
        }
        btnLogout.setOnClickListener {
            (activity as MainActivity).logout()
        }
    }

    private fun editUserInfo() {
        val intent = Intent(this.context, UserInfo::class.java)
        startActivity(intent)
    }

    private fun addMyDogs() {
        val intent = Intent(this.context, MyDogPage::class.java)
        startActivity(intent)
    }

    private fun openReviewPage() {
        TODO("Not yet implemented")
    }

    private fun openNotificationPage() {
        TODO("Not yet implemented")
    }

    private fun enrollHotel(userId: String) {
        val intent = Intent(this.context, OwnerEnrollHotel::class.java).apply {
            putExtra(USER_ID, userId)
        }
        startActivity(intent)
    }

    private fun changeView(userId: String) {

    }
}
