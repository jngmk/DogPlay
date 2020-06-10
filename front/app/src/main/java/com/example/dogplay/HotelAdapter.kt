package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.home_list.view.*
import kotlinx.android.synthetic.main.home_list.view.Address
import kotlinx.android.synthetic.main.home_list.view.eval
import kotlinx.android.synthetic.main.home_list.view.hotelName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelAdapter(var context:Context, var hotels:ArrayList<Hotel>) :
    RecyclerView.Adapter<HotelAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var currentHotel : Hotel? = null
        var currentPosition : Int = 0

        init {
            itemView.setOnClickListener{
            }
        }
        fun setData(hotel:Hotel,pos: Int){
            itemView.hotelName.text = hotel.hotelname
            itemView.eval.text = "${hotel.star} / 5.0"
            itemView.review.text = "후기 ${hotel.countreview}"
            itemView.Address.text = hotel.address
            itemView.price.text = "${hotel.minprice}원"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.home_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hotels.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val server = server()
        val hotel = hotels[position]
        holder.setData(hotel,position)
        var picture = hotel.picture
        var likes = 0
        server!!.checkLike(hotel.hotelnumber,Supplier.UserId)
            .enqueue(object :Callback<checkLikes>{
                override fun onFailure(call: Call<checkLikes>, t: Throwable) {
                }
                override fun onResponse(call: Call<checkLikes>, response: Response<checkLikes>) {
                    likes = response.body()!!.data
                    if (likes == 0){
                        holder.itemView.like.setImageResource(R.drawable.heart_empty_foreground)
                    } else{
                        holder.itemView.like.setImageResource(R.drawable.heart_fulled_foreground)
                    }
                }
            })
        holder.itemView.like.setOnClickListener {
            if (likes == 1){
                server.deleteLike(hotel.hotelnumber,Supplier.UserId)
                    .enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                        }
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            holder.itemView.like.setImageResource(R.drawable.heart_empty_foreground)
                            likes = 0
                        }
                    })

            } else{
                server.insertLike(insertLike(hotel.hotelnumber,Supplier.UserId))
                    .enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                        }
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            holder.itemView.like.setImageResource(R.drawable.heart_fulled_foreground)
                            likes = 1
                        }
                    })
            }
        }
        if (picture != null){
            Glide.with(holder.itemView)
                .load(picture)
                .into(holder.itemView.cardImg)
        } else{
            holder.itemView.cardImg.setImageResource(R.drawable.dog)
        }
        holder.itemView.setOnClickListener{
            var server = server()

            server!!.searchHotelDetail(hotels[position].hotelnumber).enqueue(object :
                Callback<HotelDetailDTO> {
                override fun onFailure(call: Call<HotelDetailDTO>, t: Throwable) {
                    Log.d("faile", t.toString())
                }

                override fun onResponse(
                    call: Call<HotelDetailDTO>,
                    response: Response<HotelDetailDTO>
                ) {
                    val data: HotelDetailDTO = response.body()!!
                    val hotelDetailData = data
                    Supplier.SelectHotel = hotelDetailData

                    Log.d("리뷰", Supplier.SelectHotel.data.toString())
//                    Log.d("방",Supplier.SelectHotel.data.HotelRoom.toString())
                    val intent = Intent(context,HotelDetail::class.java)
                    context.startActivity(intent)
                }
            })
        }

    }
}