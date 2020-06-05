package com.example.dogplay.ui.owner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogplay.R
import kotlinx.android.synthetic.main.edit_page.*
import kotlinx.android.synthetic.main.host_edit_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EditPage : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val hotelnumber = "892-11-00104"
        var roomname = ""
        var int_id = 0
        var roomDetailData: Array<HashMap<String, Any>>
        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)

        server.searchRoomDetail(hotelnumber).enqueue(object : Callback<RoomDTO> {
            override fun onFailure(call: Call<RoomDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }
            override fun onResponse(call: Call<RoomDTO>, response: Response<RoomDTO>) {
                val data:RoomDTO = response.body()!!
                roomDetailData = data!!.data
                var roomList = arrayListOf<EditRooms>()

                for (room in roomDetailData) {
                    Log.d("success", room.toString())
                    roomList.add(EditRooms(R.drawable.dog2, room["roomname"].toString(), room["count"].toString().split(".")[0].toInt()))
                }
                println("${roomList[0].toString()} 테스트")

                rv_edit.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rv_edit.setHasFixedSize(true)
                rv_edit.adapter = EditPageAdapter(roomList)
            }
        })



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_page, container, false)
    }

}