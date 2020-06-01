package com.example.dogplay

import com.google.gson.internal.LinkedTreeMap
import java.net.Inet4Address

data class Hotel(var counstar: Int, var address: String, var star:Double, var distance:Int, var latitude:Double, val userid:String, var hashid:ArrayList<Int>, var hotelnumber:String, var countreview:Int, var minprice:Int, var contact:String, var detail:ArrayList<ArrayList<String>>, var hotelname:String, var longitude:Double, var info:String, var hashtag:ArrayList<String>)


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
    var HotelDetailReview = arrayListOf<LinkedTreeMap<String,Any>>()
    var HotelDetailInfo = ArrayList<ArrayList<String>>()
    var RoomDetail = ArrayList<LinkedTreeMap<String,Any>>()
    var PickAdress = String()
    var UserId = String()
    var DMList = ArrayList<DMset>()
}

