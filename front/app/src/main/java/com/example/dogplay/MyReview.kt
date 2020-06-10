package com.example.dogplay

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ActionMenuView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.all_review_item.view.*
import kotlinx.android.synthetic.main.my_review.*
import kotlinx.android.synthetic.main.my_review_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.all_review_item.view.reviewAt as reviewAt1

class MyReview :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_review)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        ReviewRecycler.layoutManager = layoutManager
        val server = server()
        server!!.searchReviewById(Supplier.UserId)
            .enqueue(object : Callback<SearchReviewDTO>{
                override fun onFailure(call: Call<SearchReviewDTO>, t: Throwable) {
                }
                override fun onResponse(
                    call: Call<SearchReviewDTO>,
                    response: Response<SearchReviewDTO>
                ) {
                    Log.d("내 리뷰좀 볼까", response.body()!!.data.toString())
                    ReviewRecycler.adapter = MyReviewAdapter(applicationContext, response.body()!!.data)
                }

            })
    }
}
class MyReviewAdapter(var context: Context, var MyReviews:ArrayList<SearchReview>):
    RecyclerView.Adapter<MyReviewAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.my_review_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return MyReviews.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var myReview = MyReviews[position]
        val server = server()
        server!!.findHotelName(myReview.hotelnumber).enqueue(object :Callback<findHotelName>{
            override fun onFailure(call: Call<findHotelName>, t: Throwable) {
                Log.d("안된건가?", t.toString())
            }

            override fun onResponse(call: Call<findHotelName>, response: Response<findHotelName>) {
                holder.itemView.hotelName.text = response.body()!!.data
            }
        })
        holder.itemView.myStar.rating = myReview.star.toFloat()
        val reviewView = LayoutInflater.from(context).inflate(R.layout.all_review_item, null, false)
        reviewView.profileText.text = myReview.userid.split("@")[0]
        val date = myReview.created
        holder.itemView.reviewAt.text = "${date[0]}-${date[1]}-${date[2]} ${date[3]}:${date[4]}"
        holder.itemView.myReviewContent.text = myReview.content
        server.searchByReview(myReview.id).enqueue(object :Callback<SearchResponseDTO>{
            override fun onFailure(call: Call<SearchResponseDTO>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<SearchResponseDTO>,
                response: Response<SearchResponseDTO>
            ) {
                if (response.body()!!.data == null){
                    Log.d("사장 리뷰 없음", "없다")
                } else{
                    holder.itemView.ownerReview.text = response.body()!!.data.content
                }
            }

        })
    }
}
