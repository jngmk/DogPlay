package com.example.dogplay

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dogplay.API.Companion.kserver
import com.example.dogplay.API.Companion.server
import kotlinx.android.synthetic.main.all_review.*
import kotlinx.android.synthetic.main.all_review_item.view.*
import kotlinx.android.synthetic.main.all_review_item.view.profileText
import kotlinx.android.synthetic.main.all_review_item.view.reviewAt
import kotlinx.android.synthetic.main.all_review_item.view.reviewContent
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.cart_page.*
import kotlinx.android.synthetic.main.owner_response.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllReview:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_review)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        ReviewRecycler.layoutManager = layoutManager
        SelectHotelName.text = Supplier.SelectHotel.data.HotelStar.hotelname
        val server = server()
        server!!.searchByHotel(Supplier.SelectHotel.data.HotelStar.hotelnumber).enqueue(object :Callback<SearchReviewDTO>{
            override fun onFailure(call: Call<SearchReviewDTO>, t: Throwable) {
                Log.d("리뷰 검색 실패", t.toString())
            }

            override fun onResponse(
                call: Call<SearchReviewDTO>,
                response: Response<SearchReviewDTO>
            ) {
                Log.d("리뷰 검색 성공", response.body()!!.data.toString())
                var ReviewResponseSet = ArrayList<SearchReview>()
                val datas = response.body()!!.data
                for (data in datas){
                    server.searchByReview(data.id).enqueue(object :Callback<SearchResponseDTO>{
                        override fun onFailure(call: Call<SearchResponseDTO>, t: Throwable) {
                            Log.d("사장님 리뷰 검색 실패", t.toString())
                        }

                        override fun onResponse(
                            call: Call<SearchResponseDTO>,
                            response: Response<SearchResponseDTO>
                        ) {
                            Log.d("사장님 리뷰 검색 성공", response.body()!!.data.toString())
                            val rData = response.body()!!.data
                            ReviewResponseSet.add(data)
                            ReviewResponseSet.add(SearchReview(rData.id,Supplier.SelectHotel.data.HotelStar.hotelnumber,rData.userid,-1,5.0,rData.created,rData.content))
                            Log.d("아마 비동기 떄문", ReviewResponseSet.toString())
                            val adapter = AllReviewAdapter(applicationContext,ReviewResponseSet)
                            ReviewRecycler.adapter = adapter
                        }
                    })
                }
            }
        })
    }
}

class AllReviewAdapter(var context: Context, var Reviews:ArrayList<SearchReview>):
    RecyclerView.Adapter<AllReviewAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
        var view:View
        if (viewType == -1){
            view = LayoutInflater.from(context).inflate(R.layout.owner_response ,parent, false)
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.all_review_item ,parent, false)
        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Reviews.size
    }

    override fun getItemViewType(position: Int): Int {
        return Reviews[position].visitid
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Review = Reviews[position]
        val date = Review.created
        if (Review.visitid == -1){
            holder.itemView.reviewContent.text = Review.content
            holder.itemView.reviewAt.text = "${date[0]}-${date[1]}-${date[2]} ${date[3]}:${date[4]}:${date[5]}"
        } else {
            holder.itemView.profileText.text = Review.userid
            holder.itemView.reviewContent.text = Review.content
            holder.itemView.reviewAt.text = "${date[0]}-${date[1]}-${date[2]} ${date[3]}:${date[4]}:${date[5]}"
        }
    }
}
