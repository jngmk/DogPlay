package com.example.dogplay

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.hotel_detail.*

class HotelDetail:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_detail)
        if (intent.hasExtra("img")){
            var img = intent.getStringExtra("img")
            Toast.makeText(this,"hi i'm"+img, Toast.LENGTH_LONG).show()
            var imgId = this.getResources().getIdentifier(img,"drawable",this.getPackageName())
            mainImg.setImageResource(imgId)
        }
    }
}