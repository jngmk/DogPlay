package com.example.dogplay

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.info_list.view.*

class InfoAdapter(var context:Context, var infos:ArrayList<ArrayList<String>>) :
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
        Log.d("짜자잔", info.toString())
        holder.itemView.infoListTitle.text = info[0]
        holder.itemView.infoListText.text = info[1]
//        holder.itemView.setOnClickListener{
//        }
    }
}