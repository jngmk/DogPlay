package com.example.dogplay.ui.owner

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
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
import com.google.firebase.storage.FirebaseStorage
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dogplay.API
import com.example.dogplay.HotelInfoToPost
import com.example.dogplay.HotelPictureToPost
import com.example.dogplay.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_owner_enorll_hotel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OwnerEnrollHotel : AppCompatActivity() {
    private lateinit var mViewPager2: ViewPager2
    private lateinit var firestore: FirebaseFirestore
    private lateinit var hotelPicture: HotelPictureToPost
    private var hotelData: HotelInfoToPost = HotelInfoToPost()
    private val pictures: ArrayList<Bitmap> = ArrayList()
    private val IMAGE_GALLERY_REQUEST_CODE = 1001

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_enorll_hotel)

        mViewPager2 = vpEnrollHotelImg

        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        btnEnrollHotelImg.setOnClickListener {
            getImages()
        }
        btnEnrollHotelData.setOnClickListener {
            postData()
        }

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getImages() {
//        Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "image/*"
//            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
//        }
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
        }
    }

    private fun postData() {
        val server = API.server()
        hotelData.apply {
            hotelname = findViewById<EditText>(R.id.edtEnrollHotelName).text.toString()
            hotelnumber = findViewById<EditText>(R.id.edtEnrollBN).text.toString().toInt()
            address = findViewById<EditText>(R.id.edtEnrollHotelAddress).text.toString()
            contact = findViewById<EditText>(R.id.edtEnrollHotelContact).text.toString()
            info = findViewById<EditText>(R.id.edtEnrollInfo).text.toString()
        }

        server!!.postHotelInfo(hotelData).enqueue(object :
            Callback<HotelInfoToPost> {
            override fun onFailure(call: Call<HotelInfoToPost>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<HotelInfoToPost>, response: Response<HotelInfoToPost>) {
                Log.d("success",response.body().toString())

                // create new storage
                val document = firestore.collection("hotels").document(hotelData.hotelnumber.toString())

                hotelData.let {
                    val hotelName = it.hotelname
                    val hotelNumber = it.hotelnumber
                    for (i in 0..pictures.size) {
                        val set = document.set(pictures[i])
                        hotelPicture.apply {
                            name = "$hotelName$i"
                            hotelnumber = hotelNumber
                            this.picture = "hotels/$set"
                        }
                        postPictures()
                    }
                }
            }
        })
    }

    class PagerRecyclerAdapter(private val bitmaps: ArrayList<Bitmap>) : RecyclerView.Adapter<PagerViewHolder>() {

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
                    val count = data.clipData!!.itemCount - 1
                    for (i in 0..count) {
                        val picture = data.clipData!!.getItemAt(i)
                        val source = ImageDecoder.createSource(this.contentResolver, picture.uri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        pictures.add(bitmap)
                    }
                    mViewPager2.adapter = PagerRecyclerAdapter(pictures)
                    mViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                }
            }
        }
    }

    private fun postPictures() {
        val server = API.server()

        server!!.postHotelPictures(hotelPicture).enqueue(object :
            Callback<HotelPictureToPost> {
            override fun onFailure(call: Call<HotelPictureToPost>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<HotelPictureToPost>, response: Response<HotelPictureToPost>) {
                Log.d("success",response.body().toString())
                clearHotelPicture()
            }
        })
    }

    private fun clearHotelPicture() {
        hotelPicture.apply {
            name = ""
            picture = ""
            hotelnumber = 0
        }
    }
}
