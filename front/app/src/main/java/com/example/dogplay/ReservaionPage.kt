package com.example.dogplay

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogplay.API.Companion.server
import com.example.dogplay.databinding.AllReviewItemBinding.inflate
import kotlinx.android.synthetic.main.cart_item.view.*
import kotlinx.android.synthetic.main.cart_page.view.*
import kotlinx.android.synthetic.main.home_list.view.*
import kotlinx.android.synthetic.main.home_list.view.cardImg
import kotlinx.android.synthetic.main.post_reservation_page.view.*
import kotlinx.android.synthetic.main.pre_reservation_page.view.*
import kotlinx.android.synthetic.main.reservation_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class preReservationAdapter(var context: Context, var reservations:ArrayList<Reservation>):
    RecyclerView.Adapter<preReservationAdapter.ViewHolder>(){
    val server = server()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.pre_reservation_page ,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Pre
        val reservation = reservations[position]
        Log.d("호텔번호뭔데", reservation.hotelnumber)
        holder.itemView.preDate.text = "${reservation.startdate[0]}.${reservation.startdate[1]}.${reservation.startdate[2]} ~ ${(reservation.finishdate[1]-reservation.startdate[1])*30 + (reservation.finishdate[2]-reservation.startdate[2])} 박"
        server!!.searchHotelStar(reservation.hotelnumber).enqueue(object :Callback<HotelDTO>{
            override fun onFailure(call: Call<HotelDTO>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<HotelDTO>,
                response: Response<HotelDTO>
            ) {
                Log.d("뭔데", response.body().toString())
                holder.itemView.prehotelName.text = response.body()!!.data.hotelname
                val picture = response.body()!!.data.picture
                holder.itemView.prehotelName.text = response.body()!!.data.hotelname
                holder.itemView.preAddress.text = response.body()!!.data.address
                holder.itemView.preItemName.text = reservation.roomname
                if (picture != null){
                    Glide.with(holder.itemView)
                        .load(picture)
                        .into(holder.itemView.cardImg)
                } else{
                    holder.itemView.cardImg.setImageResource(R.drawable.dog)
                }
                holder.itemView.writeBtn.setOnClickListener{
                    val builder = AlertDialog.Builder(context)
                    val dialogView = LayoutInflater.from(context).inflate(R.layout.review_form, null, false)
                    val dialogText = dialogView.findViewById<EditText>(R.id.dialogEt)
                    val dialogRatingBar = dialogView.findViewById<RatingBar>(R.id.dialogRb)
                    builder.setView(dialogView)
                        .setPositiveButton("확인"){
                            dialog, i ->
                            val score = dialogRatingBar.rating.toDouble()
                            Log.d("별 몇개", dialogText.text.toString())
                            server.insertReview(InsertReview(dialogText.text.toString(),"",response.body()!!.data.hotelnumber,0,score,Supplier.UserId,0))
                                .enqueue(object :Callback<Any>{
                                    override fun onFailure(call: Call<Any>, t: Throwable) {
                                    }
                                    override fun onResponse(
                                        call: Call<Any>,
                                        response: Response<Any>
                                    ) {
                                        server.updateReservation(InsertReservation(reservation.count,"hello",
                                            LocalDateTime.of(reservation.finishdate[0],reservation.finishdate[1],reservation.finishdate[2],reservation.finishdate[3],reservation.finishdate[4],reservation.finishdate[5]).toString(),
                                        reservation.hotelnumber,reservation.id,reservation.paidid,reservation.phone!!,reservation.roomname,
                                            LocalDateTime.of(reservation.finishdate[0],reservation.finishdate[1],reservation.finishdate[2],reservation.finishdate[3],reservation.finishdate[4],reservation.finishdate[5]).toString(),
                                        reservation.userid,1)).enqueue(object :Callback<Any>{
                                            override fun onFailure(call: Call<Any>, t: Throwable) {
                                            }
                                            override fun onResponse(
                                                call: Call<Any>,
                                                response: Response<Any>
                                            ) {
                                                Log.d("수정 되었어요", response.body().toString())
                                            }

                                        })
                                        Toast.makeText(context,"리뷰 등록이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                                    }

                                })
                            //버튼 눌렀을 때
                        }
                        .setNegativeButton("취소"){
                            dialog, i ->
                        }
                        .show()
                }
            }
        })
    }
}

class postReservationAdapter(var context: Context, var reservations:ArrayList<Reservation>):
    RecyclerView.Adapter<postReservationAdapter.ViewHolder>(){
    val server = server()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        init {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.post_reservation_page ,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reservations.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.itemView.postDate.text = "${reservation.startdate[0]}.${reservation.startdate[1]}.${reservation.startdate[2]} ~ ${(reservation.finishdate[1]-reservation.startdate[1])*30 + (reservation.finishdate[2]-reservation.startdate[2])} 박"
        server!!.searchHotelStar(reservation.hotelnumber).enqueue(object :Callback<HotelDTO>{
            override fun onFailure(call: Call<HotelDTO>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<HotelDTO>,
                response: Response<HotelDTO>
            ) {
                val picture = response.body()!!.data.picture
                holder.itemView.postHotelName.text = response.body()!!.data.hotelname
                holder.itemView.postAddress.text = response.body()!!.data.address
                holder.itemView.postItemName.text =reservation.roomname
                if (picture != null){
                    Glide.with(holder.itemView)
                        .load(picture)
                        .into(holder.itemView.cardImg)
                } else{
                    holder.itemView.cardImg.setImageResource(R.drawable.dog)
                }
            }
        })
    }
}

class ReservationPage : Fragment() {
    companion object {
        fun newInstance() = ReservationPage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reservation_page, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val server = server()
        Log.d("예약페이지", "")
        server!!.searchReservationByUser("test1").enqueue(object :Callback<ReservationDTO>{
            override fun onFailure(call: Call<ReservationDTO>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<ReservationDTO>,
                response: Response<ReservationDTO>
            ) {
                val instance = Calendar.getInstance()
                val year = instance.get(Calendar.YEAR)
                val month = instance.get(Calendar.MONTH)+1
                val day = instance.get(Calendar.DATE)
                Log.d("day", day.toString())
                Log.d("예약", response.body().toString())
                Supplier.PreReservation = ArrayList<Reservation>()
                Supplier.PostReservation = ArrayList<Reservation>()
                for (i in response.body()!!.data){
                    if(i.finishdate[0] < year || i.finishdate[1] < month || i.finishdate[2] < day){
                        Supplier.PreReservation.add(i)
                        Log.d("전에꺼", "$month $day, ${i.finishdate[1]} ${i.finishdate[2]}")
                    } else {
                        Supplier.PostReservation.add(i)
                        Log.d("뒤에꺼", "$month $day, ${i.finishdate[1]} ${i.finishdate[2]}")
                    }
                }
                val layoutManager = LinearLayoutManager(context)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                ReservationRecycler.layoutManager = layoutManager
                Log.d("예정된 예약" ,Supplier.PostReservation.toString())
                Log.d("이전 예약", Supplier.PreReservation.toString())
                var adapter = postReservationAdapter(requireContext(),Supplier.PostReservation)
                ReservationRecycler.adapter = adapter
            }
        })

        postReservation.setOnClickListener{
            var adapter = postReservationAdapter(requireContext(),Supplier.PostReservation)
            ReservationRecycler.adapter = adapter
            Log.d("목록", Supplier.PostReservation.toString())
        }
        preReservation.setOnClickListener{
            var adapter = preReservationAdapter(requireContext(),Supplier.PreReservation)
            ReservationRecycler.adapter = adapter
        }

    }
}