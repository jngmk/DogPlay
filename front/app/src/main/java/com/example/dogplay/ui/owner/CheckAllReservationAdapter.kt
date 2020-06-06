package com.example.dogplay.ui.owner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.R

class CheckAllReservationAdapter(val checkReservationList: ArrayList<CheckReservations>) : RecyclerView.Adapter<CheckAllReservationAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckAllReservationAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.check_reservation_list, parent, false)
        return CustomViewHolder(view)

    }

    override fun getItemCount(): Int {
        return checkReservationList.size
    }

    override fun onBindViewHolder(holder: CheckAllReservationAdapter.CustomViewHolder, position: Int) {
        holder.name.text = checkReservationList.get(position).name.toString()
        holder.contact.text = checkReservationList.get(position).contact.toString()
        holder.time.text = checkReservationList.get(position).time.toString()
//        holder.count.text = checkReservationList.get(position).count.toString()
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_reservation_name)
        val contact = itemView.findViewById<TextView>(R.id.tv_reservation_contact)
        val time = itemView.findViewById<TextView>(R.id.tv_reservation_time)
        val count = itemView.findViewById<TextView>(R.id.tv_reservation_count)
    }
}