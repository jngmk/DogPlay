package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_list.view.*
import kotlinx.android.synthetic.main.home_list.view.address
import kotlinx.android.synthetic.main.home_list.view.eval
import kotlinx.android.synthetic.main.home_list.view.hotelName
import kotlinx.android.synthetic.main.hotel_detail.view.*

class RoomAdapter(var context:Context, var rooms:List<Room>) :
    RecyclerView.Adapter<RoomAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
                Toast.makeText(context,"hi i'm", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.room_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hotel = rooms[position]
        holder.itemView.setOnClickListener{
            Toast.makeText(context,"hi i'm" + rooms[position].title!!, Toast.LENGTH_LONG).show()
        }
    }
}