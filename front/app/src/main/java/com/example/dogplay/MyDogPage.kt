package com.example.dogplay
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setMargins
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.dogplay.API.Companion.server
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.hotel_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.internal.LinkedTreeMap
import kotlinx.android.synthetic.main.my_dog_list.*
import kotlinx.android.synthetic.main.room_detail.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MyDogPage:AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_dog_list)
        var cnt = 0
        var dp:Float
        dp = resources.displayMetrics.density
        PlusDog.setBackground(ShapeDrawable(OvalShape()))
        PlusDog.layoutParams.height = 80*dp.toInt()
        PlusDog.layoutParams.width = 80*dp.toInt()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            PlusDog.clipToOutline = true
        }
        PlusDog.setOnClickListener{
            val img = ImageView(this)
            img.setImageResource(R.drawable.dog3)
            img.background = ShapeDrawable(OvalShape())
            img.clipToOutline = true
            val layoutParams = LinearLayout.LayoutParams(80*dp.toInt(),80*dp.toInt())
            layoutParams.topMargin = 10*dp.toInt()
            layoutParams.leftMargin = 10*dp.toInt()
            layoutParams.bottomMargin= 10*dp.toInt()
            layoutParams.rightMargin = 10*dp.toInt()
            img.layoutParams = layoutParams
            img.setOnClickListener{
                val sameImg = ImageView(this)
                sameImg.setImageResource(R.drawable.dog3)
                sameImg.background=ShapeDrawable(OvalShape())
                sameImg.clipToOutline=true
                val layoutParams = LinearLayout.LayoutParams(80*dp.toInt(),80*dp.toInt())
                layoutParams.topMargin = 10*dp.toInt()
                layoutParams.leftMargin = 10*dp.toInt()
                layoutParams.bottomMargin= 10*dp.toInt()
                layoutParams.rightMargin = 10*dp.toInt()
                sameImg.layoutParams = layoutParams
                sameImg.setOnClickListener{
                    SelectDog.removeView(sameImg)
                }
                SelectDog.addView(sameImg)

            }
            MyDogList.addView(img,cnt)
            cnt++
        }

    }
}



