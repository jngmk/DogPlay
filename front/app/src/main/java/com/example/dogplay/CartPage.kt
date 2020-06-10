package com.example.dogplay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dogplay.API.Companion.kserver
import com.example.dogplay.API.Companion.server
import com.example.dogplay.ui.owner.OwnerEnrollHotel
import kotlinx.android.synthetic.main.activity_owner_enorll_hotel.*
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.cart_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartPage:AppCompatActivity(){
    var totalPrice = 0
    val pg_token = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_page)
        Supplier.SelectPayRoom = hashMapOf<Pair<String,Int>,Int>()
        CheckInDate.text = Supplier.SelectDateView[0]
        CheckOutDate.text = Supplier.SelectDateView[1]
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        CartList.layoutManager = layoutManager
        HotelName.text = Supplier.SelectHotel.data.HotelStar.hotelname
        val server = server()
        var day =1
        server!!.calcTotalPrice(Supplier.UserId).enqueue(object :Callback<calcTotalPrice>{
            override fun onFailure(call: Call<calcTotalPrice>, t: Throwable) {
                Log.d("장바구니 총 가격 구하기 실패", t.toString())
            }
            override fun onResponse(
                call: Call<calcTotalPrice>,
                response: Response<calcTotalPrice>
            ) {
                totalPrice = response.body()!!.data
                day = ((Supplier.SelectDate[1] - Supplier.SelectDate[0])/86400000).toInt()
                Log.d("날짜 가져온거", Supplier.SelectDate.toString())
                Log.d("몇일인지 보자",day.toString())
                TotalPrice.text = "${totalPrice*day}"
                Supplier.totalCartPrice = totalPrice*day
            }

        })
        server!!.searchCartById(Supplier.UserId).enqueue(object :Callback<responseCartDTO>{
            override fun onFailure(call: Call<responseCartDTO>, t: Throwable) {
                Log.d("왜 안된다고 말을 못해!", t.toString())
            }

            override fun onResponse(
                call: Call<responseCartDTO>,
                response: Response<responseCartDTO>
            ) {
                Log.d("될줄 알구이써따구", response.body().toString())
                val adapter = CartAdapter(applicationContext,response.body()!!.data){
                    TotalPrice.text = "${Supplier.totalCartPrice*day}"
                }
                CartList.adapter = adapter
                SubmitCart.setOnClickListener{
//                    val kserver = kserver()
                    Supplier.SelectPayForm = PayForm("TC0ONETIME",Supplier.SelectHotel.data.HotelStar.userid,Supplier.UserId,Supplier.SelectHotel.data.HotelStar.hotelname,
                        1,Supplier.totalCartPrice,0,"http://k02a4021.p.ssafy.io:8080/api/v1/paid/kakaopay",
                        "http://k02a4021.p.ssafy.io:8080/api/v1/paid/usercancel","http://k02a4021.p.ssafy.io:8080/api/v1/paid/failkakaopay")
                    server!!.kakaoPay(Supplier.SelectPayForm).enqueue(object :Callback<kakaoReadyDTO>{
                        override fun onFailure(call: Call<kakaoReadyDTO>, t: Throwable) {
                            Log.d("카카오 페이 실패", t.toString())
                        }
                        override fun onResponse(call: Call<kakaoReadyDTO>, response: Response<kakaoReadyDTO>) {
                            Log.d("카카오 페이 성공", response.body().toString())
//                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(response.body()!!.data.next_redirect_mobile_url))
//                            startActivityForResult(intent,pg_token)
                            val intent = Intent(applicationContext,KakaoWebView::class.java)
                            intent.putExtra("url", response.body()!!.data.next_redirect_app_url)
                            Supplier.ResponseTid = response.body()!!.data.tid
                            startActivity(intent)
//                            val intent = Intent.parseUri(response.body()!!.data.next_redirect_mobile_url, Intent.URI_INTENT_SCHEME)
//                            startActivity(intent)
                        }
                    })
                }
            }
        })
    }
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("결제 잘 끝났어?", "${data}")
        Toast.makeText(applicationContext, "예약되었습니다.",Toast.LENGTH_SHORT).show()
    }
}

class CartAdapter(var context: Context, var CartItems:ArrayList<responseCart>, val itemClick:()->Unit):
    RecyclerView.Adapter<CartAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View, itemClick: () -> Unit) : RecyclerView.ViewHolder(itemView){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cart_item ,parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return CartItems.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val CartItem = CartItems[position]

        getPicture(CartItem.hotelnumber, CartItem.roomname, holder.itemView)

        var roomPrice = 10000
        Supplier.SelectHotel.data.HotelRoom.forEach run@{
            if (CartItem.roomname == it.roomname){
                roomPrice = it.price
                return@run
            }
        }
        var cnt = CartItem.price/roomPrice
        Supplier.SelectPayRoom[Pair(CartItem.roomname, position)] = cnt
        holder.itemView.CartItemName.text = CartItem.roomname
        holder.itemView.roomPrice.text = "${roomPrice!!}"
        holder.itemView.calcPrice.text = "${roomPrice!!*cnt}"
        holder.itemView.RoomCnt.text = "${cnt}개"
        holder.itemView.Plus.setOnClickListener{
            cnt++
            holder.itemView.RoomCnt.text = "${cnt}개"
            holder.itemView.calcPrice.text = "${roomPrice*cnt}"
            Supplier.totalCartPrice += roomPrice
            Supplier.SelectPayRoom[Pair(CartItem.roomname, position)] = cnt
            itemClick()
        }
        holder.itemView.Minus.setOnClickListener{
            if (cnt > 1){
                cnt--
                holder.itemView.RoomCnt.text = "${cnt}개"
                holder.itemView.calcPrice.text = "${roomPrice*cnt}"
                Supplier.totalCartPrice -= roomPrice
                Supplier.SelectPayRoom[Pair(CartItem.roomname, position)] = cnt
                itemClick()
            }
        }
    }

    private fun getPicture(hotelnumber: String, name: String, itemView: View) {
        val server = server()
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
                    val picture = response.body()!!.data[0].picture
                    if (picture != "") {
                        Glide.with(itemView)
                            .load(picture)
                            .into(itemView.CartItemImg)
                    } else {
                        itemView.CartItemImg.setImageResource(R.drawable.dog)
                    }
                }
            }
        })

    }
}
