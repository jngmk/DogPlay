package com.example.dogplay.ui.owner

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.view.children
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dogplay.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_owner_edit_hotel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OwnerEditHotel : AppCompatActivity() {
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mViewAdapter: PagerAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mHotelDetailLayout: LinearLayout
    private lateinit var firestore: FirebaseFirestore
    private lateinit var imageRef: StorageReference
    private lateinit var userId: String
    private lateinit var hotelNumber: String
    private var photoId = 0

    private val editPhotoData: UpdatePhotoDTO = UpdatePhotoDTO()
    private var hotelPicture: HotelPicture = HotelPicture(0,"","","")
    private var hotelHash: HotelHashToPost = HotelHashToPost()
    private var hotelData: HotelInfoToPost = HotelInfoToPost()
    private var hotelDetailsLayoutIdx = 3
    private val hotelDetailsIdx: ArrayList<Int> = ArrayList()
    private val hotelDetails: ArrayList<ArrayList<EditText>> = ArrayList()
    private var storageReferenence = FirebaseStorage.getInstance().getReference()
    private val pictures: ArrayList<Bitmap> = ArrayList()
    private val uris: ArrayList<Uri> = ArrayList()
    private val IMAGE_GALLERY_REQUEST_CODE = 1001
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_edit_hotel)

        hotelNumber = intent.getStringExtra(HOTEL_NUMBER)!!

        var hotelDetailData:HashMap<String,Any> = hashMapOf()
        var photoData:HashMap<String,Any> = hashMapOf()

        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)

        server.getPhotoInfo(hotelNumber).enqueue(object:Callback<PhotoDTO> {
            override fun onFailure(call: Call<PhotoDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
                Log.d("faile", "${hotelNumber}")

            }

            override fun onResponse(call: Call<PhotoDTO>, response: Response<PhotoDTO>) {
                val data:PhotoDTO = response.body()!!
                if (data!!.data.isNotEmpty()){
                photoData = data!!.data[0]
                photoId = photoData["id"].toString().split('.')[0].toInt()
                Log.d("faile", "성공 사진 아이디${photoId}-----------------------------------")
            }}

        })


        server.searchHotelDetail(hotelNumber).enqueue(object : Callback<HotelDTO> {
            override fun onFailure(call: Call<HotelDTO>, t: Throwable) {
                Log.d("faile", t.toString())
                Log.d("faile", "실패-----------------------------------")
            }

            override fun onResponse(call: Call<HotelDTO>, response: Response<HotelDTO>) {
                val data:HotelDTO = response.body()!!
                hotelDetailData = data!!.data
                val str_hotelname = hotelDetailData["hotelname"].toString()
                edtEditHotelName.setText(str_hotelname)
                val str_address = hotelDetailData["address"].toString()
                edtEditHotelAddress.setText(str_address)
                val str_contact = hotelDetailData["contact"].toString()
                edtEditHotelContact.setText(str_contact)
                val str_hotelnumber = hotelDetailData["hotelnumber"].toString()
                edtEditBN.setText(str_hotelnumber)
                val str_info = hotelDetailData["info"].toString()
                edtEditInfo.setText(str_info)

            }

        })

        mViewPager2 = vpEditHotelImg
        mRecyclerView = rcyEditHotelTag
        mHotelDetailLayout = layoutEditHotelDetail
        userId = Supplier.user.userid

        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        getHashTag()

        btnEditHotelBack.setOnClickListener {
            finish()
        }
        btnEditHotelImg.setOnClickListener {
            getImages()
        }
        btnExitEditHotel.setOnClickListener {
            val idx = mViewPager2.currentItem
            pictures.removeAt(idx)
            uris.removeAt(idx)
            mViewAdapter.notifyDataSetChanged()
            if (pictures.size == 0) {
                it.visibility = View.GONE
            }
        }
        btnPlusEditHotelDetail.setOnClickListener {
            val dp = resources.displayMetrics.density
            val sp = TypedValue.COMPLEX_UNIT_SP

            // layout idx
            val layoutIdx = hotelDetailsLayoutIdx

            // 제목과 삭제 버튼을 담을 레이아웃
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL

            val linearParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            linearParams.topMargin = (16 * dp).toInt()

            linearLayout.layoutParams = linearParams

            // 제목
            val edtHotelDetailTitle = EditText(this)
            edtHotelDetailTitle.hint = resources.getString((R.string.enterTheTitle))
            edtHotelDetailTitle.setTextSize(sp, 14F)
            edtHotelDetailTitle.backgroundTintList = getColorStateList(R.color.transparent)
            edtHotelDetailTitle.inputType = InputType.TYPE_CLASS_TEXT

            val titleParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1F)

            edtHotelDetailTitle.layoutParams = titleParams

            // 내용
            val edtHotelDetailContent = EditText(this)
            edtHotelDetailContent.hint = resources.getString(R.string.enterTheContent)
            edtHotelDetailContent.setTextSize(sp, 16F)
            edtHotelDetailContent.inputType = InputType.TYPE_CLASS_TEXT
            edtHotelDetailContent.setPadding(10,26,10,(32 * dp).toInt())

            val contentParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            edtHotelDetailContent.layoutParams = contentParams

            // 삭제버튼
            val btnDeleteHotelDetail = ImageView(this)
            btnDeleteHotelDetail.setImageResource(R.drawable.exit_foreground)

            val btnParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, (16 * dp).toInt(), 1F)
            btnParams.gravity = Gravity.CENTER_VERTICAL

            btnDeleteHotelDetail.layoutParams = btnParams

            // 삭제 이벤트
            btnDeleteHotelDetail.setOnClickListener {
                mHotelDetailLayout.removeView(linearLayout)
                mHotelDetailLayout.removeView(edtHotelDetailTitle)
                mHotelDetailLayout.removeView(edtHotelDetailContent)
                mHotelDetailLayout.removeView(btnDeleteHotelDetail)

                val count = hotelDetailsIdx.size - 1
                var removeIdx = 0
                for (i in 0..count) {
                    val value = hotelDetailsIdx[i]
                    if (layoutIdx == value) {
                        removeIdx = i
                        break
                    }
                }
                hotelDetailsIdx.removeAt(removeIdx)
                hotelDetails.removeAt(removeIdx)
            }

            linearLayout.addView(edtHotelDetailTitle)
            linearLayout.addView(btnDeleteHotelDetail)
            mHotelDetailLayout.addView(linearLayout)
            mHotelDetailLayout.addView(edtHotelDetailContent)

            hotelDetailsIdx.add(layoutIdx)
            val content: ArrayList<EditText> = ArrayList()
            content.add(edtHotelDetailTitle)
            content.add(edtHotelDetailContent)
            hotelDetails.add(content)
            hotelDetailsLayoutIdx++
        }
        btnEditHotelData.setOnClickListener {
            if (edtEditHotelName.text == null || edtEditHotelName.text.toString() == "") {
                Toast.makeText(this, "호텔 이름을 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (pictures.size == 0) {
                Toast.makeText(this, "호텔 사진을 등록해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEditHotelAddress.text == null || edtEditHotelAddress.text.toString() == "") {
                Toast.makeText(this, "호텔 주소를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEditHotelContact.text == null && edtEditHotelContact.text.toString() == "") {
                Toast.makeText(this, "호텔 연락처를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEditBN.text == null && edtEditBN.text.toString() == "") {
                Toast.makeText(this, "사업자 등록번호를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEditInfo.text == null && edtEditInfo.text.toString() == "") {
                Toast.makeText(this, "호텔 소개를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else {
                postData()
            }
//            postData()
        }

    }

    private fun getHashTag() {
        val server = API.server()
        // 근처에 있는 호텔 정보 가져오기
        server!!.getHashTag().enqueue(object : Callback<HashTagDTO> {
            override fun onFailure(call: Call<HashTagDTO>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<HashTagDTO>, response: retrofit2.Response<HashTagDTO>) {
                Log.d("success",response.body().toString())
                val hashTagList: HashTagDTO? = response.body()
                mRecyclerView.adapter = RecyclerAdapter(hashTagList!!.data)
                mRecyclerView.layoutManager = GridLayoutManager(this@OwnerEditHotel, 3, RecyclerView.VERTICAL, false)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getImages() {
//        Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "image/*"
//            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
//        }
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
        }
    }

    private fun postData() {
        hotelData.apply {
            userid = userId
            hotelname = findViewById<EditText>(R.id.edtEditHotelName).text.toString()
            hotelnumber = findViewById<EditText>(R.id.edtEditBN).text.toString()
            address = findViewById<EditText>(R.id.edtEditHotelAddress).text.toString()
            contact = findViewById<EditText>(R.id.edtEditHotelContact).text.toString()
            info = findViewById<EditText>(R.id.edtEditInfo).text.toString()
        }
        // Details 추가하기
        val title1 = findViewById<EditText>(R.id.edtEditHotelDetailTitle1).text.toString()
        val content1 = findViewById<EditText>(R.id.edtEditHotelDetailContent1).text.toString()
        val title2 = findViewById<EditText>(R.id.edtEditHotelDetailTitle2).text.toString()
        val content2 = findViewById<EditText>(R.id.edtEditHotelDetailContent2).text.toString()

        hotelData.detail.addProperty(title1, content1)
        hotelData.detail.addProperty(title2, content2)

        hotelDetails.forEach {
                hotelDetail ->
            val title = hotelDetail[0].text.toString()
            val content = hotelDetail[1].text.toString()
            hotelData.detail.addProperty(title, content)
        }

        val server = API.server()
        server!!.putHotelInfo(hotelData).enqueue(object :
            Callback<HotelReturnData> {
            override fun onFailure(call: Call<HotelReturnData>, t: Throwable) {
                Toast.makeText(applicationContext, "호텔 수정에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<HotelReturnData>, response: Response<HotelReturnData>) {
                Toast.makeText(applicationContext, "호텔이 성공적으로 수정되었습니다.", Toast.LENGTH_LONG).show()

                // 선택된 hashTag 추가하기
                mRecyclerView.children.forEach {
                    val checkBox: CheckBox = it as AppCompatCheckBox
                    if (checkBox.isChecked) {
                        hotelHash.hashtag = checkBox.tag.toString().toInt()
                        hotelHash.hotelnumber = hotelData.hotelnumber
                        postHotelHash()
                    }
                }

                // firebase 에 사진 올리기
                hotelData.let {
                    var categoryName = "detail"
                    val hotelNumber = it.hotelnumber
                    val count = pictures.size - 1

                    for (i in 0..count) {
                        val uri = uris[i]
                        if (i == 0) {
                            imageRef = storageReferenence.child("hotels/$hotelNumber/" + uri.lastPathSegment)
                            categoryName = "main"
                        }
                        else {
                            imageRef = storageReferenence.child("hotels/$hotelNumber/detail/" + uri.lastPathSegment)
                        }
                        val uploadTask = imageRef.putFile(uri)
                        uploadTask.addOnSuccessListener {
                            val downloadUrl = imageRef.downloadUrl
                            downloadUrl.addOnSuccessListener {
                                // 데이터 베이스에 사진 url 올리기
                                if (photoId == 0) {
                                    hotelPicture.apply {
                                    name = categoryName
                                    hotelnumber = hotelNumber
                                    picture = it.toString()
                                }
                                    postPictures()
                                } else {
                                editPhotoData.apply {
                                    name = categoryName
                                    hotelnumber = hotelNumber
                                    picture = it.toString()
                                    id = photoId

                                }
                                putPictures()}
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

    private fun postHotelHash() {
        val server = API.server()

        server!!.postHotelHash(hotelHash).enqueue(object :
            Callback<HotelReturnData> {
            override fun onFailure(call: Call<HotelReturnData>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<HotelReturnData>, response: Response<HotelReturnData>) {
                Log.d("success",response.body().toString())
                clearHotelHash()
            }
        })
    }

    private fun clearHotelHash() {
        hotelHash.apply {
            id = 0
            hashtag = 0
            hotelnumber = ""
        }
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

    private fun putPictures() {
        var retrofit = Retrofit.Builder()
            .baseUrl("http://k02a4021.p.ssafy.io:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server = retrofit.create(EditService::class.java)

        server.putPhotoInfo(editPhotoData).enqueue(object :
            Callback<ReturnData> {
            override fun onFailure(call: Call<ReturnData>, t: Throwable) {
                Log.d("fail",t.toString())
            }

            override fun onResponse(call: Call<ReturnData>, response: Response<ReturnData>) {
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

    class RecyclerAdapter(private val hashTags: ArrayList<HashTag>) : RecyclerView.Adapter<RecyclerViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
            RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.owner_hotel_tag_item, parent, false))

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val hashTag = hashTags[position]
            holder.updateHotelImage(hashTag, position)
        }

        override fun getItemCount(): Int = hashTags.size
    }

    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var checkBox: CheckBox = itemView.findViewById(R.id.btnHotelTag)

        fun updateHotelImage(hashTag: HashTag, position: Int) {
            checkBox.text = hashTag.name
            checkBox.tag = hashTag.id
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_CODE) {
                if (data != null && data.data != null) {
                    pictures.clear()
                    uris.clear()
                    val image = data.data
                    val source = ImageDecoder.createSource(this.contentResolver, image!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)

                    pictures.add(bitmap)
                    uris.add(image)
                    fetchAdapter()

                }
                else if (data != null && data.clipData != null) {  // data: stuff back to us, data.data: image uri user select
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
                    fetchAdapter()

                }
            }
        }
    }

    private fun fetchAdapter() {
        mViewAdapter = PagerAdapter(pictures)
        mViewPager2.adapter = mViewAdapter
        mViewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        if (pictures.size > 0) {
            btnExitEditHotel.visibility = View.VISIBLE
        }
    }

}
