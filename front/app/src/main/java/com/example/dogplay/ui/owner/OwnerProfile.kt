package com.example.dogplay.ui.owner

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogplay.MainActivity

import com.example.dogplay.R
import com.example.dogplay.UserInfo
import kotlinx.android.synthetic.main.fragment_owner_profile.*


class OwnerProfile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userId = "owner6"

        btnUserInfo.setOnClickListener {
            editUserInfo()
        }
        btnNotification.setOnClickListener {
            openNotificationPage()
        }
        btnEnrollMyHotel.setOnClickListener {
            enrollHotel(userId)
        }
        btnChangeToUserView.setOnClickListener {
            changeView()
        }
        btnLogout.setOnClickListener {
            (activity as MainActivity).logout()
        }
    }

    private fun editUserInfo() {
        val intent = Intent(this.context, UserInfo::class.java)
        startActivity(intent)
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

    private fun changeView() {

    }
}
