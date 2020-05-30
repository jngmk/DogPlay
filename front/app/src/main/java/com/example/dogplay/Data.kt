package com.example.dogplay

import com.google.gson.internal.LinkedTreeMap

data class Hotel(var title: String, var img:String, var eval:String, var review:String, var address:String, var price:String)
//data class Hotel(var address: String, var contact:String, var detail:String, var hashid:String, var hotelname:String, var hotelnumber:Long, var info:String, var latitude:Double, var longitude:Double, var userid:String)
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
}

