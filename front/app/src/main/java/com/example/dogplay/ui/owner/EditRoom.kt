package com.example.dogplay.ui.owner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dogplay.R
import kotlinx.android.synthetic.main.host_edit_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EditRoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_edit_room)

        var int_id = 0
        var roomname = ""

        if(intent.hasExtra("roomname")) {
            roomname = intent.getStringExtra("roomname").toString()
        }

        var roomDetailData: HashMap<String, Any>
        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)

        server.searchHotelRoomDetail("892-11-00104", "${roomname}").enqueue(object : Callback<EditRoomDTO> {

            override fun onFailure(call: Call<EditRoomDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }

            override fun onResponse(call: Call<EditRoomDTO>, response: Response<EditRoomDTO>) {
                val data:EditRoomDTO = response.body()!!
                roomDetailData = data!!.data

                Log.d("sucess", "성공-----------------------------------")
                int_id = roomDetailData["id"].toString().split(".")[0].toInt()
                Log.d("sucess", int_id.toString())

                var str_roomname = roomDetailData["roomname"].toString()
                et_room_name.setText(roomname)
                var int_price = roomDetailData["price"].toString().split(".")[0].toInt()
                et_room_price.setText(int_price.toString())
                var int_min_size = roomDetailData["minsize"].toString().split(".")[0].toInt()
                tv_min_weight.text = int_min_size.toString()
                var int_max_size = roomDetailData["maxsize"].toString().split(".")[0].toInt()
                tv_max_weight.text = int_max_size.toString()
                var int_count = roomDetailData["count"].toString().split(".")[0].toInt()
                tv_room_count.text = int_count.toString()

                iv_room_count_plus.setOnClickListener {
                    int_count += 1
                    tv_room_count.text = int_count.toString()
                }

                iv_room_count_minus.setOnClickListener {
                    int_count -= 1
                    tv_room_count.text = int_count.toString()
                }

                iv_min_weight_plus.setOnClickListener {
                    int_min_size += 1
                    tv_min_weight.text = int_min_size.toString()
                }

                iv_min_wiehgt_minus.setOnClickListener {
                    int_min_size -= 1
                    tv_min_weight.text = int_min_size.toString()
                }
            }
        })
        btn_delete.setOnClickListener {
//            server.deleteRoom(int_id).enqueue(object: Callback<RoomDeleteDTO>{
//                override fun onFailure(call: Call<RoomDeleteDTO>, t: Throwable) {
//                    Log.d("faile", "실패-----------------------------------")
//                }
//
//                override fun onResponse(call: Call<RoomDeleteDTO>, response: Response<RoomDeleteDTO>) {
//                    Log.d("sucess", "성공-----------------------------------")
//                }
//
//            })
            val intent = Intent(this, EditPage::class.java)
            startActivity(intent)
            finish()


        }

    }
}