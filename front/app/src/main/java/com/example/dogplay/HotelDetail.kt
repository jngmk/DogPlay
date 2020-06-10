package com.example.dogplay
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.dogplay.API.Companion.server
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.hotel_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class HotelDetail:AppCompatActivity(), OnMapReadyCallback{
    private lateinit var mViewPager: ViewPager2
    private lateinit var targetUser: User
    private var hotelPictures: ArrayList<HotelPicture> = ArrayList()
    private var roomPictures: ArrayList<HotelPicture> = ArrayList()
    private lateinit var targetId: String
    val server = server()
    var likes = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hotel_detail)

        targetId = Supplier.SelectHotel.data.HotelStar.userid

        server!!.getUserByUserId(targetId).enqueue(object : Callback<UserDTO> {
            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }

            override fun onResponse(call: Call<UserDTO>, response: Response<UserDTO>) {
                targetUser = response.body()!!.data
            }
        })
        server!!.checkLike(Supplier.SelectHotel.data.HotelStar.hotelnumber,Supplier.UserId)
            .enqueue(object :Callback<checkLikes>{
                override fun onFailure(call: Call<checkLikes>, t: Throwable) {
                }
                override fun onResponse(call: Call<checkLikes>, response: Response<checkLikes>) {
                    likes = response.body()!!.data
                    if ( likes == 0){
                        like.setImageResource(R.drawable.heart_empty_foreground)
                    } else{
                        like.setImageResource(R.drawable.heart_fulled_foreground)
                    }
                }
            })

        like.setOnClickListener{
            if (likes == 0){
                server.insertLike(insertLike(Supplier.SelectHotel.data.HotelStar.hotelnumber,Supplier.UserId))
                    .enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                        }
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            like.setImageResource(R.drawable.heart_fulled_foreground)
                            likes = 1
                        }
                    })
            } else{
                server.deleteLike(Supplier.SelectHotel.data.HotelStar.hotelnumber,Supplier.UserId)
                    .enqueue(object :Callback<Any>{
                        override fun onFailure(call: Call<Any>, t: Throwable) {
                        }
                        override fun onResponse(call: Call<Any>, response: Response<Any>) {
                            like.setImageResource(R.drawable.heart_empty_foreground)
                            likes = 0
                        }
                    })
            }
        }
        mViewPager = mainImg

        getPicture()

        paybtn.setOnClickListener{
            server!!.searchCartById(Supplier.UserId).enqueue(object :Callback<responseCartDTO>{
                override fun onFailure(call: Call<responseCartDTO>, t: Throwable) {
                    Log.d("호텔디테일 PayButton 오류", t.toString())
                }
                override fun onResponse(
                    call: Call<responseCartDTO>,
                    response: Response<responseCartDTO>
                ) {
                    Log.d("호텔디테일 장바구니검색", response.body()!!.data.toString())
                    if (response.body()!!.data.size > 0){
                        if(Supplier.SelectHotel.data.HotelStar.hotelnumber == response.body()!!.data[0].hotelnumber){
                            OnClickHandler2(View(applicationContext))
                        } else{
                            OnClickHandler(View(applicationContext))
                        }
                    } else{
                        Toast.makeText(applicationContext,"방을 선택해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("날짜 선택")

        builder.setSelection(Supplier.datepickerSelection)

        val dateRangePicker = builder.build()
        dm.setOnClickListener{
            val intent = Intent(applicationContext,DirectMessage::class.java)
            intent.putExtra("targetId", targetId)
            intent.putExtra("targetName", targetUser.nickname)
            intent.putExtra("targetPicture", targetUser.picture ?: "")
            startActivity(intent)

//            server!!.chatTwoPeople(Supplier.UserId,Supplier.SelectHotel.data.HotelStar.userid).enqueue(object :Callback<DMDTO>{
//                override fun onFailure(call: Call<DMDTO>, t: Throwable) {
//                }
//                override fun onResponse(call: Call<DMDTO>, response: Response<DMDTO>) {
//                    var DMList = ArrayList<DMset>()
//                    if (response.body()!!.data.size == 0){
//                        Log.d("호텔 주인", Supplier.SelectHotel.data.HotelStar.userid)
//                        server.getChatroom(getChatRoom(0)).enqueue(object :Callback<chatRoom>{
//                            override fun onFailure(call: Call<chatRoom>, t: Throwable) {
//                            }
//                            override fun onResponse(
//                                call: Call<chatRoom>,
//                                response: Response<chatRoom>
//                            ) {
//                                Log.d("챗룸을 받나",response.body()!!.data.toString())
//                                server!!.PostChatInsert(ChatInsert(response.body()!!.data,"",0,"${Supplier.SelectHotel.data.HotelStar.userid.split('@')[0]}님에게 문의드립니다.",
//                                    "",1,"${Supplier.SelectHotel.data.HotelStar.userid}", "${Supplier.UserId}",Supplier.SelectHotel.data.HotelStar.hotelnumber)).enqueue(object :Callback<DMSend>{
//                                    override fun onFailure(call: Call<DMSend>, t: Throwable) {
//                                        TODO("Not yet implemented")
//                                    }
//
//                                    override fun onResponse(call: Call<DMSend>, response: Response<DMSend>) {
//                                        Log.d("채팅 결과", response.body().toString())
//                                        val intent = Intent(applicationContext,DirectMessage::class.java)
//                                        intent.putExtra("target", Supplier.SelectHotel.data.HotelStar.userid)
////                                        intent.putExtra("targetName", Supplier.SelectHotel.data.HotelStar.)
//                                        startActivity(intent)
//                                    }
//                                })
//                            }
//
//                        })
//                    } else {
//                        val chatid = response.body()!!.data[0].chatid
//                        server!!.PostChatInsert(ChatInsert(chatid,"",0,"${Supplier.SelectHotel.data.HotelStar.userid.split('@')[0]}님에게 문의드립니다.",
//                            "",1,"${Supplier.SelectHotel.data.HotelStar.userid}", "${Supplier.UserId}",Supplier.SelectHotel.data.HotelStar.hotelnumber)).enqueue(object :Callback<DMSend>{
//                            override fun onFailure(call: Call<DMSend>, t: Throwable) {
//                            }
//
//                            override fun onResponse(call: Call<DMSend>, response: Response<DMSend>) {
//                                Log.d("채팅 결과", response.body().toString())
//                                val intent = Intent(applicationContext,DirectMessage::class.java)
//                                intent.putExtra("target", Supplier.SelectHotel.data.HotelStar.userid)
//                                startActivity(intent)
//                            }
//                        })
//
//                    }
//                }
//            })
        }
        checkDate.setOnClickListener{
            dateRangePicker.show(supportFragmentManager, "DATE PICKER")

            dateRangePicker.addOnPositiveButtonClickListener {
                Supplier.SelectDateMain = arrayListOf(Supplier.formatterForView.format(dateRangePicker.selection!!.first),Supplier.formatterForView.format(dateRangePicker.selection!!.second))
                val date1 = dateRangePicker.selection!!.first!!
                val date2 = dateRangePicker.selection!!.second!!
                var apiDateFirst = LocalDateTime.ofInstant(Instant.ofEpochMilli(date1), ZoneId.systemDefault()).toString()
                var apiDateSecond = LocalDateTime.ofInstant(Instant.ofEpochMilli(date2), ZoneId.systemDefault()).toString()
                Log.d("포멧", apiDateFirst)
                var viewDateFirst = Supplier.formatterForApi.format(dateRangePicker.selection!!.first)
                var viewDateSecond = Supplier.formatterForApi.format(dateRangePicker.selection!!.second)
                Supplier.SelectDateApi = arrayListOf(apiDateFirst, apiDateSecond)
                Supplier.SelectDateView = arrayListOf(viewDateFirst,viewDateSecond)
                Supplier.datepickerSelection = dateRangePicker.selection!!
                cinDate.text = "${Supplier.SelectDateView[0]}"
                coutDate.text = "${Supplier.SelectDateView[1]}"
            }
        }


        Log.d("success", "Get 해결")
        var hotelPicture = Supplier.SelectHotel.data.HotelPicture
        val hotelstar = Supplier.SelectHotel.data.HotelStar
        prehotelName.text = hotelstar.hotelname
        eval.text = "${hotelstar.star}"
        preAddress.text = hotelstar.address
        reviewcnt.text = "후기 ${hotelstar.countreview}"

        cinDate.text = Supplier.SelectDateView[0]
        coutDate.text = Supplier.SelectDateView[1]

        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        roomList.layoutManager = layoutManager
        val HotelRoomData = Supplier.SelectHotel.data.HotelRoom
        val adapter = RoomAdapter(applicationContext, HotelRoomData, roomPictures)
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
            allReview.setOnClickListener{
                val intent = Intent(applicationContext,AllReview::class.java)
                startActivity(intent)
            }
        } else{
            Log.d("리뷰가 없기는 한데", "안나오네")
            allReview.text = "리뷰가 없습니다"
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

    private fun getPicture() {
        val server = server()
        server!!.getHotelAllPictures(Supplier.SelectHotel.data.HotelStar.hotelnumber).enqueue(object :Callback<HotelPicturesDTO>{
            override fun onFailure(call: Call<HotelPicturesDTO>, t: Throwable) {
                Log.d("picture error", t.toString())
            }
            override fun onResponse(
                call: Call<HotelPicturesDTO>,
                response: Response<HotelPicturesDTO>
            ) {
                Log.d("picture successs", response.body()!!.data.toString())
                val pictures = response.body()!!.data
                pictures.forEach {
                    if (it.name == "main" || it.name == "detail") {
                        hotelPictures.add(it)
                    } else {
                        roomPictures.add(it)
                    }
                }
//                Supplier.selectedRoomPictures = roomPictures
                mViewPager.adapter = PagerAdapter(hotelPictures)
                mViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                (roomList.adapter as RoomAdapter).notifyDataSetChanged()
            }
        })
    }

    fun OnClickHandler(v:View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("개놀자 알림!")
            .setMessage("장바구니에 다른 호텔의 방이 있어요! \n이전에 담은 방을 삭제하시겠어요?")
        builder.setPositiveButton("네!"){ dialogInterface: DialogInterface, i: Int ->
            val server = server()
            server!!.deleteCartById(Supplier.UserId).enqueue(object :Callback<Any>{
                override fun onFailure(call: Call<Any>, t: Throwable) {
                }

                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    Log.d("카트 삭제 완료","success")
                }

            })
        }
        builder.setNegativeButton("아니요...") { dialogInterface: DialogInterface, i: Int ->
        }

        val alertDialog = builder.create()
        alertDialog.show()
        val ntBtn = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        val ptBtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        ntBtn.background = null
        ptBtn.background = null
        ntBtn.setTextColor(Color.parseColor("#ffaaaa"))
        ptBtn.setTextColor(Color.parseColor("#ffaaaa"))
    }
    fun OnClickHandler2(v:View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("개놀자 알림!")
            .setMessage("결제 전에 이용안내를 잘 숙지하셨나요? \n이용약관을 확인하지 않으면 불이익이 생길수 있답니다 ㅠ")
        builder.setPositiveButton("네!"){ dialogInterface: DialogInterface, i: Int ->
            val intent = Intent(applicationContext,CartPage::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("아니요...") { dialogInterface: DialogInterface, i: Int ->
        }

        val alertDialog = builder.create()
        alertDialog.show()
        val ntBtn = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        val ptBtn = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        ntBtn.background = null
        ptBtn.background = null
        ntBtn.setTextColor(Color.parseColor("#ffaaaa"))
        ptBtn.setTextColor(Color.parseColor("#ffaaaa"))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val server = server()
        server!!.searchHotelDetail(Supplier.SelectHotel.data.HotelStar.hotelnumber).enqueue(object : Callback<HotelDetailDTO> {
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

    class PagerAdapter(private val pictures: ArrayList<HotelPicture>) : RecyclerView.Adapter<PagerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
            PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.owner_hotel_img_item, parent, false))

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            val picture = pictures[position]
            holder.updateHotelImage(picture)
        }

        override fun getItemCount(): Int = pictures.size
    }

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var hotelImg: ImageView = itemView.findViewById(R.id.imgEnrollHotel)

        fun updateHotelImage(picture: HotelPicture) {
            Glide.with(itemView)
                .load(picture.picture)
                .into(hotelImg)
        }
    }
}


