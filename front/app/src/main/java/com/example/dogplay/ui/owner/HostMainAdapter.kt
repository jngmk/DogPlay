package com.example.dogplay.ui.owner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.R

class HostMainAdapter(val roomList: ArrayList<MainRooms>) : RecyclerView.Adapter<HostMainAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostMainAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.host_room_list, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val room: MainRooms = roomList.get(curPos)
                Toast.makeText(parent.context, "이름: ${room.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onBindViewHolder(holder: HostMainAdapter.CustomViewHolder, position: Int) {
        holder.img.setImageResource(roomList.get(position).img)
        holder.name.text = roomList.get(position).name
        holder.cnt.text = roomList.get(position).cnt.toString()
        holder.reservation_cnt.text = roomList.get(position).reservation_cnt.toString()
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.iv_room_img)
        val name = itemView.findViewById<TextView>(R.id.tv_room_name)
        val cnt = itemView.findViewById<TextView>(R.id.tv_room_cnt)
        val reservation_cnt = itemView.findViewById<TextView>(R.id.tv_room_reservation_cnt)
    }



}