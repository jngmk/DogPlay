package com.example.dogplay

import androidx.lifecycle.MutableLiveData
import com.google.gson.internal.LinkedTreeMap
import java.net.Inet4Address
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


data class Dog(var img:String)

data class DMset(
    val user:String,
    val target:String,
    val message: String,
    val created:ArrayList<Int>,
    val readmessage:Int,
    val picture:String,
    val viewType:Int
)


object Supplier{
    var PickAdress = String()
    var UserId = "test1"
    var DMList = ArrayList<DMset>()
    var CartList = ArrayList<responseCart>()
    var SelectHotel = HotelDetailDTO(HotelDetailHash(HotelStar("","","",0.0,0.0,"","","", arrayListOf(
        arrayListOf(), arrayListOf()),0.0,0,
        arrayListOf(), arrayListOf(),0,0), arrayListOf(), arrayListOf(),
        arrayListOf()))
    var selectedRoomPictures = ArrayList<HotelPicture>()
    var totalCartPrice = 0
    var SelectHotelRoomPrice = HashMap<String,Int>()
    var SelectRoom = RoomDetailData(0,"","",0,0,0,0,"")
    var SelectPayForm = PayForm("","","","",
        0,0,0,"","","")
    var ResponseTid = ""
    val formatterForView = SimpleDateFormat("MM/dd")
    val formatterForApi = SimpleDateFormat("MM월 dd일 (EE)",Locale.KOREAN)
    var SelectDateView = arrayListOf<String>(formatterForApi.format(System.currentTimeMillis()), formatterForApi.format(System.currentTimeMillis() + 86400000) )
    var SelectDateApi = arrayListOf<String>(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() + 86400000),
        ZoneId.systemDefault()).toString(), LocalDateTime.ofInstant(
        Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()).toString())
    var SelectDateMain = arrayListOf<String>(formatterForView.format(System.currentTimeMillis()),formatterForView.format(System.currentTimeMillis() + 86400000))
    var SelectDate = arrayListOf<Long>(System.currentTimeMillis(), System.currentTimeMillis() + 86400000)
    var datepickerSelection = androidx.core.util.Pair(SelectDate[0], SelectDate[1])
    var SelectPayRoom = HashMap<Pair<String,Int>,Int>()
    var PostReservation = ArrayList<Reservation>()
    var PreReservation = ArrayList<Reservation>()
    var MainSearch = ArrayList<Hotel>()
    var user: User = User()
    var dogsSelected: ArrayList<DogInfo> = ArrayList()
    var dogs: ArrayList<DogInfo> = ArrayList()
    var dogEdit: DogInfo = DogInfo()
}

object MutableSupplier {
    var user: MutableLiveData<User> = MutableLiveData()
    var dogsSelected: MutableLiveData<ArrayList<DogInfo>> = MutableLiveData()
    var dogs: MutableLiveData<ArrayList<DogInfo>> = MutableLiveData()
}
//class UserSupplier {
//    private var _user: User = User()
//
//    internal var user : User
//        get() { return _user}
//        set(value) {_user = value}
//}


