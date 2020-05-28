package com.example.dogplay
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
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
import kotlinx.android.synthetic.main.my_dog_list.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MyDogPage:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_dog_list)
        PlusDog.setOnClickListener{
            val layout = MyDogList
            val layoutInflater = applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            val newImage = layoutInflater(R.layout.dog_list,null)
            val img = ImageView(applicationContext)
            img.layoutParams.width = 50
            img.layoutParams.height = 50
            this.addContentView(MyDogList,img)
        }

    }
}



