package com.example.dogplay
import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_detail)
        Supplier.SelectHotelRoomPrice = HashMap<String,Int>()
        val server = server()
        paybtn.setOnClickListener{
            server!!.searchCartById(Supplier.UserId).enqueue(object :Callback<responseCartDTO>{
                override fun onFailure(call: Call<responseCartDTO>, t: Throwable) {
                    Log.d("한번에 됩시다~", t.toString())
                }
                override fun onResponse(
                    call: Call<responseCartDTO>,
                    response: Response<responseCartDTO>
                ) {
                    Log.d("됐지? 돼찌?!", response.body()!!.data.toString())
                    if (response.body()!!.data.size > 0){
                        if(Supplier.SelectHotel.data.HotelStar.hotelnumber == response.body()!!.data[0].hotelnumber){
                            val intent = Intent(applicationContext,CartPage::class.java)
                            startActivity(intent)
                        } else{
                            Toast.makeText(applicationContext,"방을 선택해주세요", Toast.LENGTH_SHORT).show()
                        }
                    } else{
                        Toast.makeText(applicationContext,"방을 선택해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        Log.d("success", "Get 해결")
        var hotelPicture = Supplier.SelectHotel.data.HotelPicture
        val hotelstar = Supplier.SelectHotel.data.HotelStar
        hotelName.text = hotelstar.hotelname
        eval.text = "${hotelstar.star}"
        address.text = hotelstar.address
        reviewcnt.text = "후기 ${hotelstar.countreview}"

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        roomList.layoutManager = layoutManager
        val HotelRoomData = Supplier.SelectHotel.data.HotelRoom
        val adapter = RoomAdapter(applicationContext, HotelRoomData)
        roomList.adapter = adapter
        hotelIntro.text = hotelstar.info
        hotelLocInfo.text = hotelstar.address
        detailEval.text = "${hotelstar.star} / 5.0"

        if(Supplier.SelectHotel.data.review.size > 0){
            val vp = findViewById(R.id.reviewCarousel) as ViewPager
            val reviewAdapter = ReviewCarouselAdapter(applicationContext)
            vp.adapter = reviewAdapter
            vp.setPadding(100,0,100,0)
            vp.clipToPadding = false
            vp.setPageMargin(25)
            reviewAdapter.notifyDataSetChanged()
        } else{
            Log.d("리뷰가 없기는 한데", "안나오네")
            val textView = TextView(this)
            textView.setText("첫 리뷰를 작성해주세요")
            val layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
            textView.layoutParams = layoutParams
            val RP = findViewById<ConstraintLayout>(R.id.reviewPart)
            RP.addView(textView)
        }

        info.setOnClickListener(){
            Log.d("인포", "인포")
            val intent = Intent(applicationContext,InfoDetail::class.java)
            startActivity(intent)
        }

//            이미지 변수화 처리
//            var imgId = this.getResources().getIdentifier(img,"drawable",this.getPackageName())
//            mainImg.setImageResource(imgId)
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
        server!!.searchHotelDetail(Supplier.UserId).enqueue(object : Callback<HotelDetailDTO> {
            override fun onFailure(call: Call<HotelDetailDTO>, t: Throwable) {
            }
            override fun onResponse(
                call: Call<HotelDetailDTO>,
                response: Response<HotelDetailDTO>
            ) {
                Log.d("response", response.body()!!.data.toString())
                var lat = Supplier.SelectHotel.data.HotelStar.latitude
                var lng = Supplier.SelectHotel.data.HotelStar.longitude
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


