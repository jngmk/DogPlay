package com.example.dogplay

import com.google.gson.internal.LinkedTreeMap
import java.net.Inet4Address



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
    var totalCartPrice = 0
    var SelectHotelRoomPrice = HashMap<String,Int>()
    var SelectRoom = RoomDetailData(0,"","",0,0,0,0,"")
}

