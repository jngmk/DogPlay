package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.home_list.view.*
import kotlinx.android.synthetic.main.home_list.view.address
import kotlinx.android.synthetic.main.home_list.view.eval
import kotlinx.android.synthetic.main.home_list.view.hotelName
import kotlinx.android.synthetic.main.hotel_detail.view.*
import kotlinx.android.synthetic.main.room_list.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class RoomAdapter(var context:Context, var rooms:ArrayList<HashMap<String,Any>>) :
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
        val room:LinkedTreeMap<String, Any> = rooms[position] as LinkedTreeMap<String, Any>
        Log.d("room is", room.toString())
        holder.itemView.roomName.text = room["roomname"].toString()
        holder.itemView.roomPrice.text = "${room["price"].toString().toDouble().toInt()}원"
        holder.itemView.size.text = "${room["minsize"].toString().toDouble().toInt()}Kg ~ ${room["maxsize"].toString().toDouble().toInt()}Kg"
        holder.itemView.cnt.text = room["count"].toString().toDouble().toInt().toString()
        holder.itemView.setOnClickListener{
            val intent = Intent(context, RoomDetail::class.java)
            intent.putExtra("rno", "${room["id"].toString().toDouble().toInt()}")

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}