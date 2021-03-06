package com.example.dogplay.ui.owner

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogplay.*
import kotlinx.android.synthetic.main.owner_edit_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.ZoneId

// const val ROOM_NAME = "com.example.dogplay.ROOM_NAME"

class OwnerEditPage : AppCompatActivity() {
    private lateinit var hotelNumber: String
    private lateinit var hotelName: String
    private lateinit var reservationRoomCount: ArrayList<Int>
    private lateinit var roomPictures: ArrayList<String?>
    private lateinit var mRecyclerView: RecyclerView
    private val userId = "owner3"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.owner_edit_page)

        hotelNumber = intent.getStringExtra(HOTEL_NUMBER)!!
        hotelName = intent.getStringExtra(HOTEL_NAME)!!

        mRecyclerView = rv_main

        txtHostMainTitle.text = hotelName

        tv_edit_hotel.setOnClickListener {
            val intent = Intent(this, OwnerEditHotel::class.java).apply {
                putExtra(USER_ID, userId)
                putExtra(HOTEL_NUMBER, hotelNumber)
                putExtra(HOTEL_NAME, hotelName)
            }
            startActivity(intent)
        }

        btnHostMainBack.setOnClickListener {
            finish()
        }

        val current = LocalDateTime.now()
        val year = current.year
        val month = current.monthValue
        val date = current.dayOfMonth

        getHotelRoomData(year, month, date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getHotelRoomData(year: Int, month: Int, date: Int) {
        // 검색 날짜 설정
        val startDate = LocalDateTime.of(year, month, date, 0, 0, 0, 0).atZone(ZoneId.systemDefault()).toLocalDateTime()
        val finishDate = LocalDateTime.of(year, month, date + 1, 0, 0, 0, 0).atZone(ZoneId.systemDefault()).toLocalDateTime()

        val server = API.server()
        server!!.getRoomsSearchByHotel(hotelNumber).enqueue(object: Callback<HotelRoomDTO> {
            override fun onFailure(call: Call<HotelRoomDTO>, t: Throwable) {
                Log.d("fail", t.toString())
            }
            override fun onResponse(
                call: Call<HotelRoomDTO>, response: Response<HotelRoomDTO>
            ) {
                val rooms: ArrayList<RoomDetailData> = response.body()!!.data

                // 사진, 예약된 방 개수 초기 설정
                roomPictures = ArrayList()
                reservationRoomCount = ArrayList()
                val count = rooms.size - 1
                for (i in 0..count) {
                    reservationRoomCount.add(0)
                    roomPictures.add(null)
                    // 잔여 객실 수 구하기
                    getRoomCount(startDate, finishDate, rooms[i].roomname, i)
                    // 객실 사진 가져오기
                    getPictures(hotelNumber, rooms[i].roomname, i)
                }

                mRecyclerView.adapter = RecyclerAdapter(this@OwnerEditPage, rooms, reservationRoomCount, roomPictures)
                mRecyclerView.layoutManager = LinearLayoutManager(this@OwnerEditPage, RecyclerView.VERTICAL,false)


                (mRecyclerView.adapter as RecyclerAdapter).notifyDataSetChanged()
            }
        })
    }

    private fun getPictures(hotelnumber: String, name: String, position: Int) {
        val server = API.server()
        server!!.getHotelPictures(hotelnumber, name).enqueue(object : Callback<HotelPicturesDTO> {
            override fun onFailure(call: Call<HotelPicturesDTO>, t: Throwable) {
                Log.d("fail",t.toString())
            }
            override fun onResponse(
                call: Call<HotelPicturesDTO>,
                response: Response<HotelPicturesDTO>
            ) {
                Log.d("success",response.body().toString())
                if (response.body() != null && response.body()!!.data.size != 0) {
                    val downloadUri = response.body()!!.data[0].picture
//                    val storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(downloadUri)
                    roomPictures[position] = downloadUri
                }
                (mRecyclerView.adapter as RecyclerAdapter).notifyDataSetChanged()
            }
        })
    }


    private fun getRoomCount(startDate: LocalDateTime, finishDate: LocalDateTime, roomName: String, position: Int) {
        val server = API.server()
        server!!.getReservationRoomCount(hotelNumber, roomName, startDate, finishDate).enqueue(object: Callback<RoomCountReturnData> {
            override fun onFailure(call: Call<RoomCountReturnData>, t: Throwable) {
                Log.d("fail", t.toString())
            }
            override fun onResponse(
                call: Call<RoomCountReturnData>, response: Response<RoomCountReturnData>
            ) {
                val count: Int = response.body()!!.data
                reservationRoomCount[position] = count

                (mRecyclerView.adapter as RecyclerAdapter).notifyDataSetChanged()
            }
        })
    }


    inner class RecyclerAdapter(
        val context: Context,
        private val rooms: ArrayList<RoomDetailData>,
        private val reservationRoomCnt: ArrayList<Int>,
        private val roomPictures: ArrayList<String?>
    ) : RecyclerView.Adapter<RecyclerViewHolder>() {

        init {
            setHasStableIds(true)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.host_room_list, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val room = rooms[position]
            val picture = roomPictures[position]
            val reservationCnt = reservationRoomCnt[position]
            holder.updateHotelImage(room, picture, reservationCnt)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, OwnerEditRoom::class.java).apply {
                    putExtra(ROOM_NAME, room.roomname)
                    putExtra(HOTEL_NUMBER, hotelNumber)
                }
                context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int = rooms.size

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val roomImg: ImageView = itemView.findViewById(R.id.iv_room_img)
        private val roomName: TextView = itemView.findViewById(R.id.tv_room_name)
        private val roomSize: TextView = itemView.findViewById(R.id.size)
        private val roomTotalCnt: TextView = itemView.findViewById(R.id.tv_room_cnt)


        fun updateHotelImage(room: RoomDetailData, picture: String?, reservationCnt: Int) {
            val remainCnt = (room.count - reservationCnt).toString()
            val totalCnt = room.count.toString()
            if (picture != null) {
                Glide.with(itemView)
                    .load(picture)
                    .into(roomImg)
            } else {
                roomImg.setImageResource(R.drawable.dog)
            }
            roomName.text = room.roomname
            roomSize.text = "${room.minsize}kg ~ ${room.maxsize}kg"
            roomTotalCnt.text = "$remainCnt / $totalCnt"
        }
    }
}