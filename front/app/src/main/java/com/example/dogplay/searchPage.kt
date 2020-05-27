package com.example.dogplay

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import kotlin.Array as Array


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
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        hotelList.layoutManager = layoutManager

        val layoutManager2 = LinearLayoutManager(this.context)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        dogList.layoutManager = layoutManager2

//        keyboardListener(MainActivity(), )


        toolbar.setOnClickListener{
            searchBar.clearFocus()
        }

        val adapter = HotelAdapter(this.requireContext(), Supplier.hotels)
        hotelList.adapter = adapter

        val adapter2 = DogAdapter(this.requireContext(), Supplier.dogs)
        dogList.adapter = adapter2

        curdate.setOnClickListener{
            val intent = Intent(this.context, CalendarView::class.java)
            startActivity(intent)
        }
    }
}