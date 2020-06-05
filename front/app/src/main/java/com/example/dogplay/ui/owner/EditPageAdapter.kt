package com.example.dogplay.ui.owner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.R

class EditPageAdapter(val roomList: ArrayList<EditRooms>) : RecyclerView.Adapter<EditPageAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditPageAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.edit_room_list, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val room: EditRooms = roomList.get(curPos)
//                Toast.makeText(context, "이름: ${room.name}", Toast.LENGTH_SHORT).show()
                val intent = Intent(parent.context, EditRoom::class.java)
                intent.putExtra("roomname", roomList.get(curPos).name)
                context.startActivity(intent)


            }
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    override fun onBindViewHolder(holder: EditPageAdapter.CustomViewHolder, position: Int) {
        holder.img.setImageResource(roomList.get(position).img)
        holder.name.text = roomList.get(position).name
        holder.cnt.text = roomList.get(position).cnt.toString()

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.iv_room_img)
        val name = itemView.findViewById<TextView>(R.id.tv_room_name)
        val cnt = itemView.findViewById<TextView>(R.id.tv_room_cnt)
        var context = itemView.getContext()
    }

}