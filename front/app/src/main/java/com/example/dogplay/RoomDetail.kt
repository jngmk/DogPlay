package com.example.dogplay

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.dogplay.API.Companion.server
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.room_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomDetail:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_detail)
        val server = server()

        var cnt = 1
        var extra = intent.getStringExtra("rno")
        var HotelNo = extra
        Plus.setOnClickListener{
            cnt++
            RoomCnt.text = "${cnt}개"
        }
        Minus.setOnClickListener{
            if (cnt > 1){
                cnt--
                RoomCnt.text = "${cnt}개"
            }
        }

        server!!.searchRoomDetail(id = HotelNo).enqueue(object :Callback<RoomDetailDTO>{
            override fun onFailure(call: Call<RoomDetailDTO>, t: Throwable) {
                Log.d("설마", t.toString())
            }

            override fun onResponse(call: Call<RoomDetailDTO>, response: Response<RoomDetailDTO>) {
                Log.d("이걸로 룸 끝", response.body().toString())
                val roomDetailData = response.body()!!.data
                Supplier.SelectRoom = roomDetailData
                RoomDetailTitle.text = roomDetailData.roomname
                RoomDetailSize.text = "${roomDetailData.minsize}Kg ~ ${roomDetailData.maxsize}Kg"
                RoomDetailAddress.text = "${Supplier.PickAdress}"
                RoomDetailPrice.text = "${roomDetailData.price} 원"
                RoomIntro.text = "${roomDetailData.info}"
                subCart.setOnClickListener{
                    server!!.searchCartById(Supplier.UserId).enqueue(object :Callback<responseCartDTO>{
                        override fun onFailure(call: Call<responseCartDTO>, t: Throwable) {
                            Log.d("한번에 됩시다~", t.toString())
                        }
                        override fun onResponse(
                            call: Call<responseCartDTO>,
                            response: Response<responseCartDTO>
                        ) {
                            Log.d("됐지? 돼찌?!", response.toString())
                            if (response.body()!!.data.size > 0){
                                if(Supplier.SelectHotel.data.HotelStar.hotelnumber != response.body()!!.data[0].hotelnumber){
                                    OnClickHandler(View(applicationContext),cnt)
                                }
                            } else{
                                server.CartInsert(CartInsert(Supplier.SelectRoom.hotelnumber,Supplier.SelectRoom.id,Supplier.SelectRoom.price*cnt,Supplier.SelectRoom.roomname,Supplier.UserId)).enqueue(object :Callback<Any>{
                                    override fun onFailure(call: Call<Any>, t: Throwable) {
                                        Log.d("실패했다면 당근을 흔들어주세요", t.toString())
                                    }

                                    override fun onResponse(call: Call<Any>, response: Response<Any>) {
                                        Log.d("성공했어요 추카추카", response.body().toString())
                                        Toast.makeText(applicationContext,"장바구니에 저장되었습니다.",Toast.LENGTH_SHORT).show()
                                        onBackPressed()
                                    }
                                })
                            }
                        }
                    })

                    // 바로저장

                    // 장바구니 저장 로직
                }
            }
        })


        val vp = findViewById(R.id.RoomDetailCarousel) as ViewPager
        val RoomAdapter = RoomDetailCarouselAdapter(applicationContext)
        vp.adapter = RoomAdapter
        vp.clipToPadding = false
        RoomAdapter.notifyDataSetChanged()
        Log.d("오늘의 마지막", "")
    }

    fun OnClickHandler(v: View,cnt:Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("개놀자 알림!")
            .setMessage("장바구니에 다른 호텔의 방이 있어요! \n이전에 담은 방을 삭제하시겠어요?")
        builder.setPositiveButton("네!"){ dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(applicationContext,"없애부리!",Toast.LENGTH_SHORT).show()
            val server = server()
            server!!.deleteCartById(Supplier.UserId).enqueue(object :Callback<Any>{
                override fun onFailure(call: Call<Any>, t: Throwable) {
                }
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    server.CartInsert(CartInsert(Supplier.SelectRoom.hotelnumber,Supplier.SelectRoom.id,Supplier.SelectRoom.price*cnt,Supplier.SelectRoom.roomname,Supplier.UserId)).enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            Log.d("실패했다면 당근을 흔들어주세요", t.toString())
                        }

                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            Log.d("성공했어요 추카추카", response.body().toString())
                            Toast.makeText(applicationContext,"장바구니에 저장되었습니다.",Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        }
                    })
//                    Supplier.CartList.add(responseCart(cnt,Supplier.UserId,HotelNo,roomDetailData.roomname,roomDetailData.price))
                    Toast.makeText(applicationContext,"장바구니에 저장되었습니다.",Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }

            })
        }
        builder.setNegativeButton("아니요...") { dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(applicationContext, "아니되요...", Toast.LENGTH_SHORT).show()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}