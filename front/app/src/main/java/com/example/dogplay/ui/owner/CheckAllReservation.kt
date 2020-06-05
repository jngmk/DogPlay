package com.example.dogplay.ui.owner

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogplay.R
import kotlinx.android.synthetic.main.check_all_reservation.*

class CheckAllReservation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_all_reservation)

        var roomname = ""

        if(intent.hasExtra("roomname")) {
            roomname = intent.getStringExtra("roomname").toString()
        }
        tv_room_name.text = roomname

        val checkReservationList = arrayListOf(
            CheckReservations("희철이", "010-1212-5664", "2020.06.01-2020.06.03"),
            CheckReservations("양동이", "010-3545-1345", "2020.06.02-2020.06.03"),
            CheckReservations("샘물이", "010-7857-7583", "2020.06.03-2020.06.04"),
            CheckReservations("어피치", "010-6582-1576", "2020.06.03-2020.06.04"),
            CheckReservations("아이폰", "010-3222-0655", "2020.06.04-2020.06.05")
        )

        rv_all_reservation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_all_reservation.setHasFixedSize(true)
        rv_all_reservation.adapter = CheckAllReservationAdapter(checkReservationList)

    }

}