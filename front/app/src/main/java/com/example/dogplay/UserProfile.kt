package com.example.dogplay

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dogplay.ui.owner.LoginActivity
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.LogoutResponseCallback
import kotlinx.android.synthetic.main.fragment_user_profile.*

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnUserInfo.setOnClickListener {
            editUserInfo()
        }
        btnNotification.setOnClickListener {
            openNotificationPage()
        }
        btnReviews.setOnClickListener {
            openReviewPage()
        }
        btnEnrollMyHotel.setOnClickListener {
            enrollHotel()
        }
        btnChangeToOwnerView.setOnClickListener {
            changeView()
        }
        btnLogout.setOnClickListener {
            (activity as MainActivity).logout()
        }
    }

    private fun editUserInfo() {
        TODO("Not yet implemented")
    }

    private fun openNotificationPage() {
        TODO("Not yet implemented")
    }

    private fun openReviewPage() {
        TODO("Not yet implemented")
    }

    private fun enrollHotel() {
        TODO("Not yet implemented")
    }

    private fun changeView() {
        TODO("Not yet implemented")
    }
}
