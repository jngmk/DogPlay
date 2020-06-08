package com.example.dogplay

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dogplay.API.Companion.server
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.opencensus.resource.Resource
import kotlinx.android.synthetic.main.map_hotel_list.view.*
import kotlinx.android.synthetic.main.map_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext


class mapPage : Fragment() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private lateinit var mMap: GoogleMap
    private lateinit var mPager: ViewPager2
    private lateinit var mMarkers: ArrayList<Marker>
    private lateinit var hotels: ArrayList<HotelInfoWithStarAndPrice>
    private var mMapCurLatitude: Double? = null
    private var mMapCurLongitude: Double? = null
    private var mCurrentMarker: Marker? = null
    private var mPrevPosition: Int? = null
    private var mapReady = false
    private var mapFocus = false
    private var numPage = 0
    private val REQUEST_ACCESS_FINE_LOCATION = 1
    private val DISTANCE = 5

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.map_page,container,false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap -> mMap = googleMap
            mapReady = true
            updateMap()
        }
        // view pager
        mPager = layout.findViewById(R.id.mapHotelViewPager)
        return layout
    }

    private fun updateMap() {
        if (mapReady) {
            mMap.setOnMarkerClickListener {
                    marker ->
                if (marker.tag != null) {
                    mPager.currentItem = marker.tag as Int
                }
                true
            }
        }
//        else {
//            val seoul = LatLng(37.4979, 1127.0276)
//            mMap.addMarker(MarkerOptions().position(seoul).title("서울"))
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 15f))
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        locationInit()
        getData()
        Log.d("지도 페이지","")
        btnSearchHotelNearBy.setOnClickListener {
            // 지도 중앙 값
            val centerLatLng:LatLng = mMap.projection.visibleRegion.latLngBounds.center
            getData(centerLatLng.latitude, centerLatLng.longitude)
        }
        btnGoCurPos.setOnClickListener {
            if (mMapCurLatitude != null && mMapCurLongitude != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(mMapCurLatitude!!, mMapCurLongitude!!), 15f))
            }
        }
    }

    private fun locationInit() {
        fusedLocationProviderClient = FusedLocationProviderClient(context!!)
        locationCallback = MyLocationCallBack()
        locationRequest = LocationRequest()

        // GPS 우선
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        /**
         * 업데이트 인터벌
         * 위치 정보가 없을 때는 업데이트 안 함
         * 상황에 따라 짧아질 수 있음, 정확하지 않음
         * 다른 앱에서 짧은 이터벌로 위치 정보를 요청하면 짧아질 수 있음
         */
        locationRequest.interval = 10000
        // 정확함. 이것보다 짧은 없데이트는 하지 않음
        locationRequest.fastestInterval = 5000
    }

    private fun getData(latitude: Double = 37.4979, longitude: Double = 1127.0276) {
        val server = API.server()
        // 근처에 있는 호텔 정보 가져오기
        server!!.getHotelNearBy(DISTANCE, latitude, longitude).enqueue(object : Callback<HotelNearByDTO> {
            override fun onFailure(call: Call<HotelNearByDTO>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<HotelNearByDTO>, response: retrofit2.Response<HotelNearByDTO>) {
                Log.d("success",response.body().toString())
                val hotelList: HotelNearByDTO? = response.body()
                mMap.clear()
                numPage = 0
                mMarkers = ArrayList()
                hotels = hotelList?.data!!
                hotels.forEach {
                    hotel ->
                    val marker = mMap.addMarker(MarkerOptions()
                        .position(LatLng(hotel.latitude, hotel.longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker))
                        .title(hotel.hotelname))
                    marker.tag = numPage
                    mMarkers.add(marker)
                    numPage++
                }
                // 초기에 0번째 페이지가 포커싱
                if (mMarkers.size != 0) {
                    val marker = mMap.addMarker(MarkerOptions()
                        .position(LatLng(hotels[0].latitude, hotels[0].longitude))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_selected_marker))
                        .title(hotels[0].hotelname))
                    mMarkers[0].remove()
                    mMarkers.add(0, marker)
                    marker.tag = 0
                }
                mPager.adapter = PagerRecyclerAdapter(context!!, hotels)
                mPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                mPager.registerOnPageChangeCallback(hotelPageChangeCallback)
            }
        })
    }

    private var hotelPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            // 이전 포커싱 초기화
            if (mPrevPosition != null) {
                mMarkers[mPrevPosition!!].remove()
                val hotel = hotels[mPrevPosition!!]
                val marker = mMap.addMarker(MarkerOptions()
                    .position(LatLng(hotel.latitude, hotel.longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker))
                    .title(hotel.hotelname))
                marker.tag = mPrevPosition!!
                mMarkers.add(mPrevPosition!!, marker)

            }
            // 현재 포커싱
            mMarkers[position].remove()
            val hotel = hotels[position]
            val marker = mMap.addMarker(MarkerOptions()
                .position(LatLng(hotel.latitude, hotel.longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_selected_marker))
                .title(hotel.hotelname))
            marker.tag = position
            mMarkers.add(position, marker)
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(hotel.latitude, hotel.longitude)))

            mPrevPosition = position
        }
    }

    override fun onResume() {
        super.onResume()
        permissionCheck()
    }

    private fun permissionCheck() {
        // 위치 권한이 있는지 검사
        if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 허용되지 않음
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(context, "현재 위치 정보를 업데이트 하기 위해선 위치 권한이 필요합니다.", Toast.LENGTH_LONG).show()
            }
            // 권한 요청
            else {
                ActivityCompat.requestPermissions(activity!!, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_ACCESS_FINE_LOCATION)
            }
        }
        // 권한을 수락했을 때
        else {
            addLocationListener()
        }
    }

    private fun addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_ACCESS_FINE_LOCATION -> {
                // 권한 허용됨
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addLocationListener()
                }
                // 권한 거부
                else {
                    Toast.makeText(context, "권한이 거부되었습니다.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        removeLocationListener()
    }

    private fun removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    inner class MyLocationCallBack: LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            val location = locationResult?.lastLocation
            location?.run {
                val now = LatLng(latitude, longitude)
                // 이전에 찍힌 마커 제거
                mCurrentMarker?.remove()
                // 현재 위치 마커 추가
                mCurrentMarker = mMap.addMarker(MarkerOptions()
                    .position(now)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_cur_pos_marker))
                    .title("현재위치"))
                if (!mapFocus) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(now, 15f))
                    getData(latitude, longitude)
                    mapFocus = true
                }
                mMapCurLatitude = latitude
                mMapCurLongitude = longitude
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity", "위도: $latitude, 경도: $longitude")
            }
        }
    }

    class PagerRecyclerAdapter(val context: Context, private val hotels: ArrayList<HotelInfoWithStarAndPrice>) : RecyclerView.Adapter<PagerViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
            PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.map_hotel_list, parent, false))

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            val hotel = hotels[position]
            holder.updateHotelList(hotel)

            holder.itemView.setOnClickListener {
                val server = server()

                server!!.searchHotelDetail(hotels[position].hotelnumber).enqueue(object :
                    Callback<HotelDetailDTO> {
                    override fun onFailure(call: Call<HotelDetailDTO>, t: Throwable) {
                        Log.d("faile", t.toString())
                    }

                    override fun onResponse(
                        call: Call<HotelDetailDTO>,
                        response: Response<HotelDetailDTO>
                    ) {
                        val data: HotelDetailDTO = response.body()!!
                        val hotelDetailData = data
                        Supplier.SelectHotel = hotelDetailData

                        Log.d("리뷰", Supplier.SelectHotel.data.toString())
//                    Log.d("방",Supplier.SelectHotel.data.HotelRoom.toString())
                        val intent = Intent(context,HotelDetail::class.java)
                        context.startActivity(intent)
                    }
                })
            }
        }

        override fun getItemCount(): Int = hotels.size
    }

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var mapHotelImg: ImageView = itemView.findViewById(R.id.mapHotelImg)
        private var mapHotelEval: TextView = itemView.findViewById(R.id.mapHotelEval)
        private var mapHotelName: TextView = itemView.findViewById(R.id.mapHotelName)
        private var mapHotelAddress: TextView = itemView.findViewById(R.id.mapHotelAddress)
        private var mapHotelPrice: TextView = itemView.findViewById(R.id.mapHotelPrice)


        @SuppressLint("SetTextI18n")
        fun updateHotelList(hotel: HotelInfoWithStarAndPrice) {
            if (hotel.picture != null) {
                Glide.with(itemView)
                    .load(hotel.picture)
                    .into(mapHotelImg)
            }
            else {
                mapHotelImg.setImageResource(R.drawable.dog)
            }

            mapHotelEval.text = hotel.star.toString()
            mapHotelName.text = hotel.hotelname
            mapHotelAddress.text = hotel.address
            val hotelMinPrice = hotel.minprice.toString()
            mapHotelPrice.text = "$hotelMinPrice ~"
        }
    }
}


