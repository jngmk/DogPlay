package com.example.dogplay.ui.owner

import android.app.ActionBar
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
import android.text.InputType
import android.text.Layout
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.storage.FirebaseStorage
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.view.children
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.dogplay.*
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.StorageReference
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_owner_enorll_hotel.*
import kotlinx.android.synthetic.main.owner_hotel_img_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class OwnerEnrollHotel : AppCompatActivity() {
    private lateinit var mViewPager2: ViewPager2
    private lateinit var mViewAdapter: PagerAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mHotelDetailLayout: LinearLayout
    private lateinit var firestore: FirebaseFirestore
    private lateinit var imageRef: StorageReference
    private lateinit var userId: String
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
        setContentView(R.layout.activity_owner_enorll_hotel)

        mViewPager2 = vpEnrollHotelImg
        mRecyclerView = rcyEnrollHotelTag
        mHotelDetailLayout = layoutEnrollHotelDetail
        userId = Supplier.user.userid

        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()

        getHashTag()

        btnEnrollHotelBack.setOnClickListener {
            finish()
        }
        btnEnrollHotelImg.setOnClickListener {
            getImages()
        }
        btnExitEnrollHotel.setOnClickListener {
            val idx = mViewPager2.currentItem
            pictures.removeAt(idx)
            uris.removeAt(idx)
            mViewAdapter.notifyDataSetChanged()
            if (pictures.size == 0) {
                it.visibility = View.GONE
            }
        }
        btnPlusEnrollHotelDetail.setOnClickListener {
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
            edtHotelDetailContent.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
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
        btnEnrollHotelData.setOnClickListener {
            if (edtEnrollHotelName.text == null || edtEnrollHotelName.text.toString() == "") {
                Toast.makeText(this, "호텔 이름을 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (pictures.size == 0) {
                Toast.makeText(this, "호텔 사진을 등록해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollHotelAddress.text == null || edtEnrollHotelAddress.text.toString() == "") {
                Toast.makeText(this, "호텔 주소를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollHotelContact.text == null && edtEnrollHotelContact.text.toString() == "") {
                Toast.makeText(this, "호텔 연락처를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollBN.text == null && edtEnrollBN.text.toString() == "") {
                Toast.makeText(this, "사업자 등록번호를 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else if (edtEnrollInfo.text == null && edtEnrollInfo.text.toString() == "") {
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
                mRecyclerView.layoutManager = GridLayoutManager(this@OwnerEnrollHotel, 3, RecyclerView.VERTICAL, false)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun getImages() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
        }
//        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
//            type = "image/*"
//            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
//        }
    }

    private fun postData() {
        hotelData.apply {
            userid = userId
            hotelname = findViewById<EditText>(R.id.edtEnrollHotelName).text.toString()
            hotelnumber = findViewById<EditText>(R.id.edtEnrollBN).text.toString()
            address = findViewById<EditText>(R.id.edtEnrollHotelAddress).text.toString()
            contact = findViewById<EditText>(R.id.edtEnrollHotelContact).text.toString()
            info = findViewById<EditText>(R.id.edtEnrollInfo).text.toString()
        }
        // Details 추가하기
        val title1 = findViewById<EditText>(R.id.edtEnrollHotelDetailTitle1).text.toString()
        val content1 = findViewById<EditText>(R.id.edtEnrollHotelDetailContent1).text.toString()
        val title2 = findViewById<EditText>(R.id.edtEnrollHotelDetailTitle2).text.toString()
        val content2 = findViewById<EditText>(R.id.edtEnrollHotelDetailContent2).text.toString()

        hotelData.detail.addProperty(title1, content1)
        hotelData.detail.addProperty(title2, content2)

        hotelDetails.forEach {
                hotelDetail ->
            val title = hotelDetail[0].text.toString()
            val content = hotelDetail[1].text.toString()
            hotelData.detail.addProperty(title, content)
        }

        val server = API.server()
        server!!.postHotelInfo(hotelData).enqueue(object :
            Callback<HotelReturnData> {
            override fun onFailure(call: Call<HotelReturnData>, t: Throwable) {
                Toast.makeText(applicationContext, "호텔 등록에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<HotelReturnData>, response: Response<HotelReturnData>) {
                Toast.makeText(applicationContext, "호텔이 성공적으로 등록되었습니다.", Toast.LENGTH_LONG).show()

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
                                hotelPicture.apply {
                                    name = categoryName
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
            btnExitEnrollHotel.visibility = View.VISIBLE
        }
    }
}
