package com.example.dogplay

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.API.Companion.kserver
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.cart_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartPage:AppCompatActivity(){
    var totalPrice = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_page)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        CartList.layoutManager = layoutManager
        HotelName.text = Supplier.SelectHotel.data.HotelStar.hotelname
        val server = server()
        server!!.calcTotalPrice(Supplier.UserId).enqueue(object :Callback<calcTotalPrice>{
            override fun onFailure(call: Call<calcTotalPrice>, t: Throwable) {
                Log.d("장바구니 총 가격 구하기 실패", t.toString())
            }
            override fun onResponse(
                call: Call<calcTotalPrice>,
                response: Response<calcTotalPrice>
            ) {
                totalPrice = response.body()!!.data
                TotalPrice.text = "${totalPrice}"
                Supplier.totalCartPrice = totalPrice
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
                    TotalPrice.text = "${Supplier.totalCartPrice}"
                }
                CartList.adapter = adapter
                val kserver = kserver()
                SubmitCart.setOnClickListener{
                    server!!.kakaoPay(PayForm("TC0ONETIME",
                        "partner_order_id",
                        Supplier.UserId,
                        "초코파이",
                        1,
                        2200,
                        0,
                        "http://k02a4021.p.ssafy.io:8080/api/v1/paid/kakaopay",
                        "http://k02a4021.p.ssafy.io:8080/api/v1/paid/usercancel",
                        "http://k02a4021.p.ssafy.io:8080/api/v1/paid/failkakaopay"
                    )).enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                            Log.d("카카오 페이 실패", t.toString())
                        }
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            Log.d("카카오 페이 성공", response.body().toString())
                        }
                    })

                }
            }
        })

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
        val roomPrice = Supplier.SelectHotelRoomPrice[CartItem.roomname]
        var cnt = CartItem.price/roomPrice!!
        holder.itemView.CartItemName.text = CartItem.roomname
        holder.itemView.roomPrice.text = "${roomPrice!!}"
        holder.itemView.calcPrice.text = "${roomPrice!!*cnt}"
        holder.itemView.RoomCnt.text = "${cnt}개"
        holder.itemView.Plus.setOnClickListener{
            cnt++
            holder.itemView.RoomCnt.text = "${cnt}개"
            holder.itemView.calcPrice.text = "${roomPrice*cnt}"
            Supplier.totalCartPrice += roomPrice
            itemClick()
        }
        holder.itemView.Minus.setOnClickListener{
            if (cnt > 1){
                cnt--
                holder.itemView.RoomCnt.text = "${cnt}개"
                holder.itemView.calcPrice.text = "${roomPrice*cnt}"
                Supplier.totalCartPrice -= roomPrice
                itemClick()
            }
        }
    }
}
