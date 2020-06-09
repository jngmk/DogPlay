package com.example.dogplay

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DogAdapter(var context:Context, private val dogs: ArrayList<DogInfo>) :
    RecyclerView.Adapter<DogAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val img = itemView.findViewById<ImageView>(R.id.imageView)

        fun updateDog(picture: String) {
            if (picture != "") {
                Glide.with(itemView)
                    .load(picture)
                    .into(img)
            }
            else {
                img.setImageResource(R.drawable.plus_foreground)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.dog_list ,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (dogs.size > 0) {
            dogs.size
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (dogs.size > 0) {
            val dog = dogs[position]
            holder.updateDog(dog.picture)
        } else {
            holder.updateDog("")
        }

        holder.itemView.setOnClickListener{
            val intent = Intent(this.context, MyDogPage::class.java)
            context.startActivity(intent)
        }
    }
}