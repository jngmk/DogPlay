package com.example.dogplay.ui.owner

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.API
import com.example.dogplay.R
import com.example.dogplay.Reservation
import com.example.dogplay.ReservationDTO
import kotlinx.android.synthetic.main.check_all_reservation.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.ZoneId

class CheckAllReservation : AppCompatActivity() {
    private lateinit var reservations: ArrayList<Reservation>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var roomName: String
    private lateinit var hotelNumber: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_all_reservation)

        roomName = intent.getStringExtra(ROOM_NAME)!!
        hotelNumber = intent.getStringExtra(HOTEL_NUMBER)!!
        mRecyclerView = rv_all_reservation

        txtCheckReservationTitle.text = roomName

        btnBack.setOnClickListener {
            finish()
        }

        cv_all_reservation.setOnDateChangeListener {
            view, year, month, dayOfMonth ->
            getData(year, month + 1, dayOfMonth)
        }
        val current = LocalDateTime.now()
        val year = current.year
        val month = current.monthValue
        val date = current.dayOfMonth

        getData(year, month, date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData(year: Int, month: Int, date: Int) {
        // 검색 날짜 설정
        val startDate = LocalDateTime.of(year, month, date, 0, 0, 0, 0).atZone(ZoneId.systemDefault()).toLocalDateTime()
        val finishDate = LocalDateTime.of(year, month, date + 1, 0, 0, 0, 0).atZone(ZoneId.systemDefault()).toLocalDateTime()

        val server = API.server()
        server!!.getReservationRoomDetail(hotelNumber, roomName, startDate, finishDate).enqueue(object: Callback<ReservationDTO> {
            override fun onFailure(call: Call<ReservationDTO>, t: Throwable) {
                Log.d("fail", t.toString())
            }
            override fun onResponse(
                call: Call<ReservationDTO>, response: Response<ReservationDTO>
            ) {
                val reservations: ArrayList<Reservation> = response.body()!!.data

                mRecyclerView.adapter = RecyclerAdapter(this@CheckAllReservation, reservations)
                mRecyclerView.layoutManager = LinearLayoutManager(this@CheckAllReservation, RecyclerView.VERTICAL,false)

            }
        })
    }

    class RecyclerAdapter(val context: Context, private val reservations: ArrayList<Reservation>) : RecyclerView.Adapter<RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.check_reservation_list, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val reservation = reservations[position]
            holder.updateHotelImage(reservation)
        }

        override fun getItemCount(): Int = reservations.size
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_reservation_name)
        private val contact: TextView = itemView.findViewById(R.id.tv_reservation_contact)
        private val time: TextView = itemView.findViewById(R.id.tv_reservation_time)
        private val count: TextView = itemView.findViewById(R.id.tv_reservation_count)

        fun updateHotelImage(reservation: Reservation) {
            val s = reservation.startdate
            val e = reservation.finishdate
            val startDate = "${s[0]}.${s[1]}.${s[2]} ${s[3]}:${s[4]}"
            val endDate = "${e[0]}.${e[1]}.${e[2]} ${e[3]}:${e[4]}"

            name.text = reservation.userid
            if (reservation.phone == null) {
                contact.text = "등록된 연락처가 없습니다."
            } else {
                contact.text = reservation.phone
            }
            time.text = "$startDate - $endDate"
            count.text = reservation.count.toString()
        }
    }

}