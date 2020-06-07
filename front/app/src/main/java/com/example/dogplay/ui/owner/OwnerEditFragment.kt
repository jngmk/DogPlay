package com.example.dogplay.ui.owner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogplay.API
import com.example.dogplay.HotelInfoWithStarAndPrice
import com.example.dogplay.HotelSearchByUserIdDTO
import com.example.dogplay.R
import kotlinx.android.synthetic.main.fragment_owner_main.*
import retrofit2.Call
import retrofit2.Callback

/**
 * A simple [Fragment] subclass.
 * Use the [OwnerMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

//const val HOTEL_NUMBER = "com.example.dogplay.HOTEL_NUMBER"
//const val HOTEL_NAME = "com.example.dogplay.HOTEL_NAME"
//const val USER_ID = "com.example.dogplay.USER_ID"

class OwnerEditFragment : Fragment() {
    private val userId = "owner3"
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var hotels: ArrayList<HotelInfoWithStarAndPrice>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRecyclerView = rcvOwnerHotel

        getData()
    }

    private fun getData() {
        val server = API.server()
        // 근처에 있는 호텔 정보 가져오기
        server!!.getHotelSearchByUserId(userId).enqueue(object : Callback<HotelSearchByUserIdDTO> {
            override fun onFailure(call: Call<HotelSearchByUserIdDTO>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<HotelSearchByUserIdDTO>, response: retrofit2.Response<HotelSearchByUserIdDTO>) {
                Log.d("success",response.body().toString())
                val hotelList: HotelSearchByUserIdDTO? = response.body()
                hotels = hotelList?.data!!

                mRecyclerView.adapter = RecyclerAdapter(context!!, hotels)
                mRecyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
            }
        })
    }

    class RecyclerAdapter(val context: Context, val hotels: ArrayList<HotelInfoWithStarAndPrice>) : RecyclerView.Adapter<RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.owner_main_hotel_item, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val hotel = hotels[position]
            holder.updateHotelImage(hotel)

            holder.itemView.setOnClickListener {
                val intent = Intent(context, OwnerEditHotel::class.java).apply {
                    putExtra(HOTEL_NUMBER, hotel.hotelnumber)
                    putExtra(HOTEL_NAME, hotel.hotelname)
                }
                context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int = hotels.size
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val hotelImg: ImageView = itemView.findViewById(R.id.cardImg)
        private val hotelName: TextView = itemView.findViewById(R.id.hotelName)
        private val hotelEval: TextView = itemView.findViewById(R.id.eval)
        private val hotelReview: TextView = itemView.findViewById(R.id.review)
        private val hotelAddress: TextView = itemView.findViewById(R.id.address)


        fun updateHotelImage(hotel: HotelInfoWithStarAndPrice) {
            if (hotel.picture != null) {
                Glide.with(itemView)
                    .load(hotel.picture)
                    .into(hotelImg)
            }
            hotelName.text = hotel.hotelname
            hotelEval.text = "${hotel.star} / 5.0"
            hotelReview.text = "후기 ${hotel.countreview}"
            hotelAddress.text = hotel.address
        }
    }
}
