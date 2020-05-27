package com.example.dogplay
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.hotel_detail.*

class HotelDetail:AppCompatActivity(), OnMapReadyCallback{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_detail)
        if (intent.hasExtra("img")){
            var img = intent.getStringExtra("img")
            Toast.makeText(this,"hi i'm"+img, Toast.LENGTH_LONG).show()
            var imgId = this.getResources().getIdentifier(img,"drawable",this.getPackageName())
            mainImg.setImageResource(imgId)
        }
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        roomList.layoutManager = layoutManager
        val adapter = RoomAdapter(this,Supplier.rooms)
        roomList.adapter = adapter

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

