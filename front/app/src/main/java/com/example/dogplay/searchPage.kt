package com.example.dogplay

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.icu.util.TimeZone
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogplay.API.Companion.server
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.datepicker.MaterialDatePicker
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter
import kotlin.collections.ArrayList
import kotlin.time.days


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
        Log.d("서치페이지", "서치")
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        MutableSupplier.dogsSelected.observe(viewLifecycleOwner, Observer {
                dogs ->
            dogList.adapter!!.notifyDataSetChanged()
            Supplier.dogsSelected = dogs
        })

        searchBar.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("서치바", query)
                var newData = ArrayList<Hotel>()
                for (i in Supplier.MainSearch){
                    if (query!! in i.hotelname){
                        newData.add(i)
                    }
                }
                hotelList.adapter = HotelAdapter(context!!,newData)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("서치바상시", newText)
                var newData = ArrayList<Hotel>()
                for (i in Supplier.MainSearch){
                    if (newText!! in i.hotelname){
                        newData.add(i)
                    }
                    else if (newText!! in i.address){
                        newData.add(i)
                    }
                }
                hotelList.adapter = HotelAdapter(context!!,newData)

                return false
            }
        })
        searchBar.setOnKeyListener(View.OnKeyListener OnkeyListener@{
                v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                Log.d("서치바", searchBar.query.toString())
                return@OnkeyListener true
            }
            false
        })
        super.onActivityCreated(savedInstanceState)
        curdate.text = "${Supplier.SelectDateMain[0]} ~ ${Supplier.SelectDateMain[1]}"

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://k02a4021.p.ssafy.io:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        var server = retrofit.create(Service::class.java)
        // retrofit server -> server() 로 대체
        val server = server()
        server!!.getRequest().enqueue(object : Callback<HotelSerchDTO> {
            override fun onFailure(call: Call<HotelSerchDTO>, t: Throwable) {
            }

            override fun onResponse(call: Call<HotelSerchDTO>, response: retrofit2.Response<HotelSerchDTO>) {
                var data = response.body()!!.data
                if (data == null){
                } else {
                    Supplier.MainSearch = data
                    Log.d("호텔찾았다", data.toString())
                    val layoutManager = LinearLayoutManager(context)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    hotelList.layoutManager = layoutManager
                    val adapter = HotelAdapter(context!!, data)
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

        val adapter2 = DogAdapter(this.requireContext(), Supplier.dogsSelected)
        dogList.adapter = adapter2

        // setting
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.clear()


        // date picker
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("날짜 선택")

        val todayPair = Pair(today, today + 86400000)
        builder.setSelection(todayPair)

        val dateRangePicker = builder.build()

        curdate.setOnClickListener{
//            val intent = Intent(this.context, CalendarView::class.java)
//            startActivity(intent)
            dateRangePicker.show(activity!!.supportFragmentManager, "DATE PICKER")

            dateRangePicker.addOnPositiveButtonClickListener {
                Supplier.SelectDateMain = arrayListOf(Supplier.formatterForView.format(dateRangePicker.selection!!.first),Supplier.formatterForView.format(dateRangePicker.selection!!.second))
                val date1 = dateRangePicker.selection!!.first!!
                val date2 = dateRangePicker.selection!!.second!!
                Supplier.SelectDate = arrayListOf(date1, date2)
                var apiDateFirst = LocalDateTime.ofInstant(Instant.ofEpochMilli(date1), ZoneId.systemDefault()).toString()
                var apiDateSecond = LocalDateTime.ofInstant(Instant.ofEpochMilli(date2), ZoneId.systemDefault()).toString()
                Log.d("포멧", apiDateFirst)
                var viewDateFirst = Supplier.formatterForApi.format(dateRangePicker.selection!!.first)
                var viewDateSecond = Supplier.formatterForApi.format(dateRangePicker.selection!!.second)
                Supplier.SelectDateApi = arrayListOf(apiDateFirst, apiDateSecond)
                Supplier.SelectDateView = arrayListOf(viewDateFirst,viewDateSecond)
                curdate.text = "${Supplier.SelectDateMain[0]} - ${Supplier.SelectDateMain[1]}"
            }
        }

        dogList.setOnClickListener{
            val intent = Intent(this.context,MyDogPage::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        curdate.text = "${Supplier.SelectDateMain[0]} ~ ${Supplier.SelectDateMain[1]}"

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://k02a4021.p.ssafy.io:8080")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        var server = retrofit.create(Service::class.java)
        // retrofit server -> server() 로 대체
        val server = server()
        server!!.getRequest().enqueue(object : Callback<HotelSerchDTO> {
            override fun onFailure(call: Call<HotelSerchDTO>, t: Throwable) {
            }

            override fun onResponse(call: Call<HotelSerchDTO>, response: retrofit2.Response<HotelSerchDTO>) {
                var data = response.body()!!.data
                if (data == null){
                } else {
                    Supplier.MainSearch = data
//                    Log.d("호텔찾았다", data.toString())
                    val layoutManager = LinearLayoutManager(context)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    hotelList.layoutManager = layoutManager
                    val adapter = HotelAdapter(context!!, data)
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

        val adapter2 = DogAdapter(this.requireContext(), Supplier.dogsSelected)
        dogList.adapter = adapter2

        // setting
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        calendar.clear()


        // date picker
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("날짜 선택")

//        val todayPair = Pair(today, today+86400000)
//        Supplier.SelectDate = arrayListOf(todayPair.first!!, todayPair.second!!)
//        Log.d("today", todayPair.toString())
        builder.setSelection(Supplier.datepickerSelection)

        val dateRangePicker = builder.build()

        curdate.setOnClickListener{
//            val intent = Intent(this.context, CalendarView::class.java)
//            startActivity(intent)
            dateRangePicker.show(activity!!.supportFragmentManager, "DATE PICKER")

            dateRangePicker.addOnPositiveButtonClickListener {
                Supplier.SelectDateMain = arrayListOf(Supplier.formatterForView.format(dateRangePicker.selection!!.first),Supplier.formatterForView.format(dateRangePicker.selection!!.second))
                val date1 = dateRangePicker.selection!!.first!!
                val date2 = dateRangePicker.selection!!.second!!
                Supplier.SelectDate = arrayListOf(date1, date2)
                var apiDateFirst = LocalDateTime.ofInstant(Instant.ofEpochMilli(date1), ZoneId.systemDefault()).toString()
                var apiDateSecond = LocalDateTime.ofInstant(Instant.ofEpochMilli(date2), ZoneId.systemDefault()).toString()
                Log.d("포멧", apiDateFirst)
                var viewDateFirst = Supplier.formatterForApi.format(dateRangePicker.selection!!.first)
                var viewDateSecond = Supplier.formatterForApi.format(dateRangePicker.selection!!.second)
                Supplier.SelectDateApi = arrayListOf(apiDateFirst, apiDateSecond)
                Supplier.SelectDateView = arrayListOf(viewDateFirst,viewDateSecond)
                Supplier.datepickerSelection = dateRangePicker.selection!!
                curdate.text = "${Supplier.SelectDateMain[0]} - ${Supplier.SelectDateMain[1]}"
            }
        }

        dogList.setOnClickListener{
            val intent = Intent(this.context,MyDogPage::class.java)
            startActivity(intent)
        }

    }
}