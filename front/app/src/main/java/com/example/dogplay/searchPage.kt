package com.example.dogplay

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogplay.API.Companion.server
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.datepicker.MaterialDatePicker
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.logging.SimpleFormatter


class searchPage : Fragment() {
    lateinit var compositeDisposable: CompositeDisposable
    private val today = MaterialDatePicker.todayInUtcMilliseconds()
    companion object {
        fun newInstance() = searchPage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // date formatter
        @SuppressLint("SimpleDateFormat")
        val formatter = SimpleDateFormat("mm/dd")
        val date = formatter.format(today)

        curdate.text = "$date - $date"

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://k02a4021.p.ssafy.io:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        var server = retrofit.create(Service::class.java)
        // retrofit server -> server() 로 대체
        val server = server()
        server!!.getRequest().enqueue(object : Callback<HotelSerchDTO> {
            override fun onFailure(call: Call<HotelSerchDTO>, t: Throwable) {
                Log.d("faile",t.toString())
            }

            override fun onResponse(call: Call<HotelSerchDTO>, response: retrofit2.Response<HotelSerchDTO>) {
                Log.d("호텔스타",response?.body().toString())
                var data: HotelSerchDTO? = response.body()
                if (data!!.data == null){
                    Log.d("호텔 뜨는거냐?","호옹")
                } else {
                    val layoutManager = LinearLayoutManager(context)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    hotelList.layoutManager = layoutManager
                    val adapter = HotelAdapter(context!!, data.data)
                    hotelList.adapter = adapter
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

        // setting
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.clear()


        // date picker
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("날짜 선택")

        val todayPair = Pair(today, today)
        builder.setSelection(todayPair)

        val dateRangePicker = builder.build()

        curdate.setOnClickListener{
//            val intent = Intent(this.context, CalendarView::class.java)
//            startActivity(intent)
            dateRangePicker.show(activity!!.supportFragmentManager, "DATE PICKER")

            dateRangePicker.addOnPositiveButtonClickListener {
                val startDate = dateRangePicker.selection!!.first
                val endDate = dateRangePicker.selection!!.second

                curdate.text = "$startDate - $endDate"
            }
        }

        dogList.setOnClickListener{
            val intent = Intent(this.context,MyDogPage::class.java)
            startActivity(intent)
        }
    }
}