package com.example.dogplay.ui.owner

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dogplay.*
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_owner_enroll_hotel_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerEnrollHotelRoom : AppCompatActivity() {
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mViewAdapter: PagerAdapter
    private var hotelPicture: HotelPicture = HotelPicture(0,"","","")
    private var storageReferenence = FirebaseStorage.getInstance().getReference()
    private var hotelRoomData: HotelRoomToPost = HotelRoomToPost()
    private val pictures: ArrayList<Bitmap> = ArrayList()
    private val uris: ArrayList<Uri> = ArrayList()
    private val IMAGE_GALLERY_REQUEST_CODE = 1002

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_enroll_hotel_room)

        mViewPager2 = vpEnrollHotelRoomImg

        btnEnrollHotelRoomBack.setOnClickListener {
            finish()
        }
        btnEnrollHotelRoomImg.setOnClickListener {
            getImages()
        }
        btnEnrollHotelRoomData.setOnClickListener {
            if (edtEnrollHotelRoomName.text == null || edtEnrollHotelRoomName.text.toString() == "") {
                Toast.makeText(this, "객실 이름을 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (pictures.size == 0) {
                Toast.makeText(this, "객실 사진을 등록해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollHotelRoomPrice.text == null || edtEnrollHotelRoomPrice.text.toString() == "") {
                Toast.makeText(this, "객실 가격을 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollHotelRoomCount.text == null && edtEnrollHotelRoomCount.text.toString() == "") {
                Toast.makeText(this, "객실 개수를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollHotelRoomMinKg.text == null && edtEnrollHotelRoomMinKg.text.toString() == "") {
                Toast.makeText(this, "수용할 수 있는 최소 몸무게를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollHotelRoomMaxKg.text == null && edtEnrollHotelRoomMaxKg.text.toString() == "") {
                Toast.makeText(this, "수용할 수 있는 최대 몸무게를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollHotelRoomInfo.text == null && edtEnrollHotelRoomInfo.text.toString() == "") {
                Toast.makeText(this, "객실 소개를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else {
                postData()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getImages() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
        }
    }

    private fun postData() {
        hotelRoomData.apply {
//            hotelnumber =
            roomname = findViewById<EditText>(R.id.edtEnrollHotelRoomName).text.toString()
            price = findViewById<EditText>(R.id.edtEnrollHotelRoomPrice).text.toString().toInt()
            count = findViewById<EditText>(R.id.edtEnrollHotelRoomCount).text.toString().toInt()
            minsize = findViewById<EditText>(R.id.edtEnrollHotelRoomMinKg).text.toString().toInt()
            maxsize = findViewById<EditText>(R.id.edtEnrollHotelRoomMaxKg).text.toString().toInt()
            info = findViewById<EditText>(R.id.edtEnrollHotelRoomInfo).text.toString()
        }

        val server = API.server()
        server!!.postHotelRoomInfo(hotelRoomData).enqueue(object :
            Callback<HotelReturnData> {
            override fun onFailure(call: Call<HotelReturnData>, t: Throwable) {
                Toast.makeText(applicationContext, "호텔 등록에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<HotelReturnData>, response: Response<HotelReturnData>) {
                Toast.makeText(applicationContext, "호텔이 성공적으로 등록되었습니다.", Toast.LENGTH_LONG).show()

                // firebase 에 사진 올리기
                hotelRoomData.let {
                    val roomName = it.roomname
                    val hotelNumber = it.hotelnumber
                    val count = pictures.size - 1

                    for (i in 0..count) {
                        val uri = uris[i]
                        val imageRef = storageReferenence.child("hotels/$hotelNumber/$roomName" + uri.lastPathSegment)
                        val uploadTask = imageRef.putFile(uri)
                        uploadTask.addOnSuccessListener {
                            val downloadUrl = imageRef.downloadUrl
                            downloadUrl.addOnSuccessListener {
                                // 데이터 베이스에 사진 url 올리기
                                hotelPicture.apply {
                                    name = roomName
                                    hotelnumber = hotelNumber
                                    picture = it.toString()
                                }
                                postPictures()
                            }
                        }
                        uploadTask.addOnFailureListener {
                            Log.e(ContentValues.TAG, it.message!!)
                        }
                    }
                }
                finish()
            }
        })
    }

    private fun postPictures() {
        val server = API.server()

        server!!.postHotelPictures(hotelPicture).enqueue(object :
            Callback<HotelReturnData> {
            override fun onFailure(call: Call<HotelReturnData>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<HotelReturnData>, response: Response<HotelReturnData>) {
                Log.d("success",response.body().toString())
                clearHotelPicture()
            }
        })
    }

    private fun clearHotelPicture() {
        hotelPicture.apply {
            name = ""
            picture = ""
            hotelnumber = ""
        }
    }

    class PagerAdapter(private val bitmaps: ArrayList<Bitmap>) : RecyclerView.Adapter<PagerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
            PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.owner_hotel_img_item, parent, false))

        override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
            val bitmap = bitmaps[position]
            holder.updateHotelImage(bitmap)
        }

        override fun getItemCount(): Int = bitmaps.size
    }

    class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var hotelImg: ImageView = itemView.findViewById(R.id.imgEnrollHotel)

        fun updateHotelImage(bitmap: Bitmap) {
            hotelImg.setImageBitmap(bitmap)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_CODE) {
                if (data != null && data.clipData != null) {  // data: stuff back to us, data.data: image uri user select
                    pictures.clear()
                    uris.clear()
                    val count = data.clipData!!.itemCount - 1
                    for (i in 0..count) {
                        val picture = data.clipData!!.getItemAt(i)
                        val source = ImageDecoder.createSource(this.contentResolver, picture.uri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        pictures.add(bitmap)
                        uris.add(picture.uri)
                    }
                    mViewAdapter = PagerAdapter(pictures)
                    mViewPager2.adapter = mViewAdapter
                    mViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                    if (pictures.size > 0) {
                        btnExitEnrollRoomHotel.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}
