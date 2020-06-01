package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.*
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_list.view.*
import kotlinx.android.synthetic.main.home_list.view.address
import kotlinx.android.synthetic.main.home_list.view.eval
import kotlinx.android.synthetic.main.home_list.view.hotelName
import kotlinx.android.synthetic.main.hotel_detail.view.*

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
        //{address=사당동, latitude=37.487366, contact=02-533-7647, detail={"위치": "역삼역에서 5.2km", "종류": "애견 카페"},
        // userid=test, hashid=, hotelnumber=1, hotelname=하늘을걷는고양이카페, longitude=126.981016, info=string}
        fun setData(hotel:Hotel,pos: Int){
            itemView.hotelName.text = hotel.hotelname
            itemView.eval.text = "${hotel.star} / 5.0"
            itemView.review.text = "후기 ${hotel.countreview}"
            itemView.address.text = hotel.address
            itemView.price.text = "${hotel.minprice}원"
            itemView.cardImg.setImageResource(R.drawable.dog)
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
        holder.itemView.setOnClickListener{
            val intent = Intent(context,HotelDetail::class.java)
            intent.putExtra("hotelnumber",hotels[position].hotelnumber.toString())
            context.startActivity(intent)
        }
    }
}