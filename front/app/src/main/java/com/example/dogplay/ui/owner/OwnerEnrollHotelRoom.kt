package com.example.dogplay.ui.owner

import android.graphics.Bitmap
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dogplay.API
import com.example.dogplay.HotelInfoToPost
import com.example.dogplay.R
import com.example.dogplay.mapPage
import retrofit2.Call
import retrofit2.Callback

class OwnerEnrollHotelRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_enroll_hotel_room)
    }
}
