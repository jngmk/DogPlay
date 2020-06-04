package com.example.dogplay.ui.owner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.R

class HostReviewAdapter(val reviewList: ArrayList<HostReviews>) : RecyclerView.Adapter<HostReviewAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostReviewAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.host_review_list, parent, false)
        return CustomViewHolder(view).apply {

            itemView.setOnClickListener {
                val curPos: Int = adapterPosition
                val review: HostReviews = reviewList.get(curPos)
                Toast.makeText(parent.context, "이름: ${review.username}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: HostReviewAdapter.CustomViewHolder, position: Int) {
        holder.username.text = reviewList.get(position).username.toString()
        holder.star.text = reviewList.get(position).star.toString()
        holder.review.text = reviewList.get(position).review.toString()
        holder.date.text = reviewList.get(position).date.toString()

    }
    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username = itemView.findViewById<TextView>(R.id.host_review_username)
        val star = itemView.findViewById<TextView>(R.id.host_review_rating)
        val review = itemView.findViewById<TextView>(R.id.host_review_review)
        val date =itemView.findViewById<TextView>(R.id.host_review_time)
        val context = itemView.getContext()
    }
}