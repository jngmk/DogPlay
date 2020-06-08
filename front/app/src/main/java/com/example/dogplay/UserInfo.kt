package com.example.dogplay

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.room_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserInfo : AppCompatActivity() {
    private var storageReferenence = FirebaseStorage.getInstance().getReference()
    private var user = Supplier.user
    private val IMAGE_GALLERY_REQUEST_CODE = 1004


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)


        edtEnterName.setText(user.nickname)
        edtEnterPhone.setText(user.phone)
        if (user.picture != "") {
            Glide.with(this)
                .load(user.picture)
                .into(imgProfile)
        }
        else {
            imgProfile.setImageResource(R.drawable.dog)
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnSaveUser.setOnClickListener {
            user.nickname = edtEnterName.text.toString()
            user.phone = edtEnterPhone.text.toString()
            saveInfo()
        }

        imgProfile.setOnClickListener {
            selectImage()
        }
    }

    private fun saveInfo() {
        val server = API.server()
        server!!.putUser(user).enqueue(object :
            Callback<HotelReturnData> {
            override fun onFailure(call: Call<HotelReturnData>, t: Throwable) {
                Toast.makeText(applicationContext, "개인 정보 수정에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<HotelReturnData>, response: Response<HotelReturnData>) {
                Toast.makeText(applicationContext, "정보가 성공적으로 수정되었습니다.", Toast.LENGTH_LONG).show()
                MutableSupplier.user.postValue(user)
                finish()
            }
        })
    }

    private fun selectImage() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            startActivityForResult(this, IMAGE_GALLERY_REQUEST_CODE)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST_CODE) {
                if (data != null && data.data != null) {  // data: stuff back to us, data.data: image uri user select
                    val image = data.data
                    val source = ImageDecoder.createSource(this.contentResolver, image!!)
                    val bitmap = ImageDecoder.decodeBitmap(source)

                    imgProfile.setImageBitmap(bitmap)

                    // firebase update
                    val imageRef = storageReferenence.child("users/${user.userid}/profile")
                    val uploadTask = imageRef.putFile(image)
                    uploadTask.addOnSuccessListener {
                        val downloadUrl = imageRef.downloadUrl
                        downloadUrl.addOnSuccessListener {
                            user.picture = it.toString()
                        }
                    }
                    uploadTask.addOnFailureListener {
                        Log.e(ContentValues.TAG, it.message!!)
                    }
                }
            }
        }
    }
}
