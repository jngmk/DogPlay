package com.example.dogplay

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Header


data class HotelSerchDTO(
    @SerializedName("data")
    var data:Array<HashMap<String,Any>>,
    @SerializedName("state")
    var state:String = ""
)

data class HotelDetailDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
)

data class HotelNearByDTO(
    @SerializedName("data")
    val data: ArrayList<HotelInfo>,
    @SerializedName("state")
    val state: String
)

data class HotelInfo(
    val hotelnumber: Int = 0,
    val userid: String,
    val hashid: String,
    val hotelname: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val contact: String,
    val info: String,
    val detail: ArrayList<ArrayList<String>>
)


interface Service {
    @GET("/api/v1/hotelstar/searchall")
    fun getRequest():Call<HotelSerchDTO>
//
    @GET("/api/v1/hoteldetail/search/")
    fun searchHotelDetail(
        @Query("hotelnumber") hotelnumber: Number
    ):Call<HotelDetailDTO>

    @GET("/api/v1/hotel/search/latitude/longitude/distance")
    fun getHotelNearBy(
        @Query("distance") distance: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<HotelNearByDTO>

    @FormUrlEncoded
    @POST("/api/v1/hotel/insert")
    fun postRequest(
        @Field("address") address:String,
        @Field("contact") contact:String,
        @Field("detail") detail:HashMap<String,String>,
        @Field("hashid") hashid:String,
        @Field("hotelname") hotelname:String,
        @Field("hotelnumber") hotelnumber:Number,
        @Field("info") info:String,
        @Field("latitude") latitude:Double,
        @Field("longitude") longitude:Double,
        @Field("userid") userid:String
    ):Call<HotelSerchDTO>
}