package com.example.dogplay

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.internal.ObjectConstructor
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

data class HotelRoomDTO(
    @SerializedName("data")
    var data:Array<HashMap<String,Any>>
)

data class HotelNearByDTO(
    @SerializedName("data")
    val data: ArrayList<HotelInfoWithStarAndPrice>,
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

data class HotelInfoWithStarAndPrice(
    val hotelnumber: Int = 0,
    val userid: String,
    val hashid: String,
    val hotelname: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val contact: String,
    val info: String,
    val detail: ArrayList<ArrayList<String>>,
    val star: Double,
    val countstar: Int,
    val countreview: Int,
    val hashtag: ArrayList<String>,
    val distance: Double,
    val minprice: Int
)

data class RoomDetailDTO(
    @SerializedName("data")
    var data : RoomDetailData

)

data class ChatMainDTO(
    @SerializedName("data")
    var data : ArrayList<ChatMain>
)

data class ChatIn(
    val id:Int,
    val chatid:Int,
    val receive:String,
    val send:String,
    val picture:String,
    val message:String,
    val created:ArrayList<Int>,
    val readmessage:Int
)

data class ChatMain(
    val chat:ChatIn,
    val count: Int
)

data class RoomDetailData(
    val id:Int,
    val hotelnumber: Int,
    val roomname :String,
    val price : Int,
    val minsize:Int,
    val maxsize:Int,
    val count:Int,
    val info:String
)

data class DMDTO(
    @SerializedName("data")
    val data:ArrayList<ChatIn>
)


interface Service {
    @GET("/api/v1/hotelstar/searchall")
    fun getRequest():Call<HotelSerchDTO>
//
    @GET("/api/v1/hoteldetail/search/")
    fun searchHotelDetail(
        @Query("hotelnumber") hotelnumber: Number
    ):Call<HotelDetailDTO>

    @GET("/api/v1/hotelstar/search/latitude/longitude/distance")
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

    @GET("/api/v1/hotelroom/search")
    fun searchRoomDetail(
        @Query("id") id:Int
    ):Call<RoomDetailDTO>

    @GET("/api/v1/chat/search/userid")
    fun searchChatWithUserId(
        @Query("userid") userid: String
    ):Call<ChatMainDTO>

    @GET("/api/v1/chat/search/receive/send")
    fun chatTwoPeople(
        @Query("receive") receive: String,
        @Query("send") send: String
    ):Call<DMDTO>
}