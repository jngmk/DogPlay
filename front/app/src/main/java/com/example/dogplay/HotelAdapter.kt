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
                Toast.makeText(context,"hi i'm", Toast.LENGTH_LONG).show()
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
        val hotel = hotels[position]
        holder.setData(hotel,position)
        var picture = hotel.picture
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