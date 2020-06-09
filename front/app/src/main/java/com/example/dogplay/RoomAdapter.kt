package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.room_list.view.*
import kotlin.collections.ArrayList

class RoomAdapter(var context:Context, var rooms:ArrayList<HotelRoom>, private val roomPictures: ArrayList<HotelPicture>) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.room_list, parent, false)
        Log.d("Detail value", rooms.toString())
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val room:HotelRoom = rooms[position]

        // pictures
        val pictures = ArrayList<HotelPicture>()
        roomPictures.forEach {
            if (it.name == room.roomname) {
                pictures.add(it)
            }
        }
        if (pictures.size > 0) {
            Glide.with(holder.itemView)
                .load(pictures[0].picture)
                .into(holder.itemView.roomImg)
        } else {
            holder.itemView.roomImg.setImageResource(R.drawable.dog)
        }


        Supplier.SelectHotelRoomPrice[room.roomname] = room.price
        Log.d("room is", room.toString())
        holder.itemView.roomName.text = room.roomname
        holder.itemView.roomPrice.text = "${room.price}원"
        holder.itemView.size.text = "${room.minsize}Kg ~ ${room.maxsize}Kg"
        holder.itemView.cnt.text = "${room.count}"
        holder.itemView.setOnClickListener{
            Supplier.selectedRoomPictures = pictures
            val intent = Intent(context, RoomDetail::class.java)
            intent.putExtra("rno", "${room.id}")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}