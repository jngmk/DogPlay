package com.example.dogplay
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.dogplay.API.Companion.server
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.hotel_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.internal.LinkedTreeMap
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class HotelDetail:AppCompatActivity(), OnMapReadyCallback{
    var hotelId:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_detail)
        var hotelDetailData:HashMap<String,Any> = hashMapOf()
        if (intent.hasExtra("hotelnumber")){
            var hotelnumber = intent.getStringExtra("hotelnumber")
            hotelId = hotelnumber.toString().toDouble().toInt()
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
                    val hotelstar = hotelDetailData["HotelStar"] as LinkedTreeMap<String,Any>
                    Log.d("What is HotelRoom", hotelDetailData["HotelStar"].toString())
                    hotelName.text = hotelstar["hotelname"].toString()
                    eval.text = hotelstar["star"].toString()
                    address.text = hotelstar["address"].toString()
                    var cntrv = hotelstar["countreview"].toString()
                    reviewcnt.text = "후기 ${cntrv}"

                    val layoutManager = LinearLayoutManager(applicationContext)
                    layoutManager.orientation = LinearLayoutManager.VERTICAL
                    roomList.layoutManager = layoutManager
                    Log.d("Hotel",hotelDetailData["HotelRoom"].toString())
                    val HotelRoomData = hotelDetailData["HotelRoom"] as ArrayList<HashMap<String,Any>>
                    Log.d("HotelRoomData", HotelRoomData.toString())
                    val adapter = RoomAdapter(applicationContext,HotelRoomData)
                    roomList.adapter = adapter
                    hotelIntro.text = hotelstar["info"].toString()
                    hotelLocInfo.text = hotelstar["address"].toString()
                    detailEval.text = "${hotelstar["star"].toString()} / 5.0"

                    Log.d("디테일", hotelDetailData["HotelRoom"].toString())
                    Supplier.HotelDetailReview = hotelDetailData["review"] as ArrayList<LinkedTreeMap<String, Any>>
                    Supplier.HotelDetailInfo = hotelstar["detail"] as ArrayList<ArrayList<String>>
                    Supplier.RoomDetail = hotelDetailData["HotelRoom"] as ArrayList<LinkedTreeMap<String,Any>>
                    Supplier.PickAdress = hotelstar["address"].toString()

                    val vp = findViewById(R.id.reviewCarousel) as ViewPager
                    val reviewAdapter = ReviewCarouselAdapter(applicationContext)
                    vp.adapter = reviewAdapter
                    vp.setPadding(100,0,100,0)
                    vp.clipToPadding = false
                    vp.setPageMargin(25)
                    reviewAdapter.notifyDataSetChanged()

                    info.setOnClickListener(){
                        val intent = Intent(applicationContext,InfoDetail::class.java)
                        startActivity(intent)
                    }
                    Log.d("내가 찾는거", hotelDetailData["review"].toString())
                }
            })
//            이미지 변수화 처리
//            var imgId = this.getResources().getIdentifier(img,"drawable",this.getPackageName())
//            mainImg.setImageResource(imgId)
        }
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.hotelLoc) as SupportMapFragment
        mapFragment.getMapAsync(this)



//        val vp = findViewById(R.id.reviewCarousel) as ViewPager
//        val reviewAdapter = ReviewCarouselAdapter(this)
//        vp.adapter = reviewAdapter
//        vp.setPadding(100,0,100,0)
//        vp.clipToPadding = false
//        vp.setPageMargin(25)
//        reviewAdapter.notifyDataSetChanged()
//
//        info.setOnClickListener(){
//            val intent = Intent(this,InfoDetail::class.java)
//            startActivity(intent)
//        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        val server = server()
        server!!.searchHotelDetail(hotelId).enqueue(object : Callback<HotelDetailDTO> {
            override fun onFailure(call: Call<HotelDetailDTO>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<HotelDetailDTO>,
                response: Response<HotelDetailDTO>
            ) {
                Log.d("response", response.body()!!.data.toString())
                var hotelstar = response.body()!!.data["HotelStar"] as LinkedTreeMap<String,Any>
                var lat = hotelstar["latitude"].toString().toDouble()
                var lng = hotelstar["longitude"].toString().toDouble()
                Log.d("lat, long", "${lat}, ${lng}")
                val tg = LatLng(lat, lng)
                googleMap.addMarker(MarkerOptions().position(tg))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tg,17f))
            }
        })
    }

    fun getPagerMarginValue(): Int {
        val metrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(metrics)
        var pageMargin = (metrics.heightPixels / 9) * 2
        return pageMargin
    }
}


