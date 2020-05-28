package com.example.dogplay
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.dogplay.API.Companion.server
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.hotel_detail.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context

class HotelDetail:AppCompatActivity(), OnMapReadyCallback{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_detail)
        var hotelDetailData:HashMap<String,Any> = hashMapOf()
        if (intent.hasExtra("hotelnumber")){
            var hotelnumber = intent.getStringExtra("hotelnumber")
            Log.d("success", "Get 해결")
            val server = server()
            server!!.searchHotelDetail(hotelnumber.toDouble().toInt()).enqueue(object : Callback<HotelDetailDTO> {
                override fun onFailure(call: Call<HotelDetailDTO>, t: Throwable) {
                    Log.d("faile", "실패")
                }

                override fun onResponse(
                    call: Call<HotelDetailDTO>,
                    response: Response<HotelDetailDTO>) {
                    Log.d("hotelDetail", response.body().toString())
                    val data:HotelDetailDTO = response.body()!!
                    hotelDetailData = data!!.data
                    hotelName.text = hotelDetailData["hotelname"].toString()
                    eval.text = hotelDetailData["star"].toString()
                    address.text = hotelDetailData["address"].toString()
                    var cntrv = hotelDetailData["countreview"].toString()
                    reviewcnt.text = "후기 ${cntrv}"

                    val layoutManager = LinearLayoutManager(applicationContext)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    roomList.layoutManager = layoutManager
                    val adapter = RoomAdapter(applicationContext,hotelDetailData["HotelRoom"] as Array<HashMap<String,Any>>)
                    roomList.adapter = adapter
                }
            })
//            이미지 변수화 처리
//            var imgId = this.getResources().getIdentifier(img,"drawable",this.getPackageName())
//            mainImg.setImageResource(imgId)
        }




        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.hotelLoc) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val vp = findViewById(R.id.reviewCarousel) as ViewPager
        val reviewAdapter = ReviewCarouselAdapter(this)
        vp.adapter = reviewAdapter
        vp.setPadding(100,0,100,0)
        vp.clipToPadding = false
        vp.setPageMargin(25)
        reviewAdapter.notifyDataSetChanged()

        info.setOnClickListener(){
            val intent = Intent(this,InfoDetail::class.java)
            startActivity(intent)
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val sydney = LatLng(37.566, 126.978)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(14f))
    }

    fun getPagerMarginValue(): Int {
        val metrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(metrics)
        var pageMargin = (metrics.heightPixels / 9) * 2
        return pageMargin
    }
}

