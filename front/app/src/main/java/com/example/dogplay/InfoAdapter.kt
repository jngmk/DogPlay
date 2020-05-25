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
import kotlinx.android.synthetic.main.info_list.view.*

class InfoAdapter(var context:Context, var infos:List<Info>) :
    RecyclerView.Adapter<InfoAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        init {
            itemView.setOnClickListener{
                Toast.makeText(context,"bow", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.info_list ,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return infos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = infos[position]
        holder.itemView.infoListTitle.text = info.title
        holder.itemView.infoListText.text = info.sub
        holder.itemView.setOnClickListener{
            Toast.makeText(context, infos[position].title!!, Toast.LENGTH_LONG).show()
        }
    }
}