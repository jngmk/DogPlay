package com.example.dogplay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogplay.API.Companion.server
import com.google.android.gms.maps.OnMapReadyCallback
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class searchPage : Fragment() {
    lateinit var compositeDisposable: CompositeDisposable
    companion object {
        fun newInstance() = searchPage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val server = server()
        server!!.getRequest().enqueue(object : Callback<HotelSerchDTO> {
            override fun onFailure(call: Call<HotelSerchDTO>, t: Throwable) {
            }

            override fun onResponse(call: Call<HotelSerchDTO>, response: retrofit2.Response<HotelSerchDTO>) {
                var data = response.body()!!.data
                var dataSize = data.size
                var pictures = ArrayList<String>()
                if (data == null){
                } else {
                    Log.d("호텔찾았다", data.toString())
                    data.forEach{
                        hotel ->
                        server.getHotelPictures(hotel.hotelnumber,hotel.hotelname).enqueue(object :Callback<HotelPicturesDTO>{
                            override fun onFailure(call: Call<HotelPicturesDTO>, t: Throwable) {
                                Log.d("실패했다네", t.toString())
                            }

                            override fun onResponse(
                                call: Call<HotelPicturesDTO>,
                                response: Response<HotelPicturesDTO>
                            ) {
                                Log.d("성공했다네", response.body().toString())
                                if (response.body()!!.data.size > 0){
                                    pictures.add(response.body()!!.data[0].picture)
                                } else {
                                    pictures.add("")
                                }
                                if (pictures.size == dataSize){
                                    val layoutManager = LinearLayoutManager(context)
                                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                                    hotelList.layoutManager = layoutManager
                                    val adapter = HotelAdapter(context!!, data, pictures)
                                    hotelList.adapter = adapter
                                }
                            }

                        })
                    }
                }
            }
        })


        val layoutManager2 = LinearLayoutManager(this.context)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        dogList.layoutManager = layoutManager2

//        keyboardListener(MainActivity(), )


        searchBar.clearFocus()
        val dogs = "@drwable/dog" // 잠시 강아지 사진 대신

        val adapter2 = DogAdapter(this.requireContext(), dogs)
        dogList.adapter = adapter2

        curdate.setOnClickListener{
            val intent = Intent(this.context, CalendarView::class.java)
            startActivity(intent)
        }

        dogList.setOnClickListener{
            val intent = Intent(this.context,MyDogPage::class.java)
            startActivity(intent)
        }
    }
}