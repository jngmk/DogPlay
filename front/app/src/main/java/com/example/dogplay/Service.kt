package com.example.dogplay

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.internal.ObjectConstructor
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Header


data class HotelSerchDTO(
    @SerializedName("data")
    var data:ArrayList<Hotel>,
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

data class HashTagDTO(
    @SerializedName("data")
    val data: ArrayList<HashTag>,
    @SerializedName("state")
    val state: String
)

data class RoomDetailDTO(
    @SerializedName("data")
    var data : RoomDetailData
)

data class ChatMainDTO(
    @SerializedName("data")
    var data : ArrayList<ChatMain>
)

data class HotelDetailHash(
    val HotelStar:HotelStar,
    val HotelPicture:ArrayList<String>,
    val review: ArrayList<String>,
    val HotelRoom: ArrayList<String>
)

data class HotelStar(
    val hotelnumber: String,
    val userid: String,
    val hotelname: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val contact: String,
    val info: String,
    val detail: ArrayList<ArrayList<String>>,
    val star:Double,
    val countreview: Int,
    val hashid: String,
    val hashtag: ArrayList<String>,
    val distance: Int,
    val minprice: Int
)

data class HotelInfo(
    val hotelnumber: String = "",
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
    val hotelnumber: String = "",
    val userid: String,
    val hashid: ArrayList<String>,
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

data class HotelInfoToPost(
    var hotelnumber: String = "",
    var userid: String = "owner1",
    var hashid: String = "",
    var hotelname: String= "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var address: String = "",
    var contact: String = "",
    var info: String = "",
    var detail: JsonObject = JsonObject()
)

data class HotelPictureToPost(
    var hotelnumber: String = "",
    var name: String = "",
    var picture: String = ""
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

data class ChatInsertForm(
    val chatid: Int
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

data class HashTag(
    val id: Int,
    val name: String
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
        @Query("hotelnumber") hotelnumber: String
    ):Call<HotelDetailDTO>

    @GET("/api/v1/hotelstar/search/latitude/longitude/distance")
    fun getHotelNearBy(
        @Query("distance") distance: Int,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Call<HotelNearByDTO>

    @FormUrlEncoded
    @POST("/api/v1/hotel/insert")
    fun postHotelInfo(
        @Body params: HotelInfoToPost
    ):Call<HotelInfoToPost>

    @POST("/api/v1/hotelpicture/insert")
    fun postHotelPictures(
        @Body params: HotelPictureToPost
    ):Call<HotelPictureToPost>

    @GET("/api/v1/hotelroom/search")
    fun searchRoomDetail(
        @Query("id") id:Int
    ):Call<RoomDetailDTO>

    @GET("/api/v1/hashtag/searchall")
    fun getHashTag():Call<HashTagDTO>

    @GET("/api/v1/chat/search/userid")
    fun searchChatWithUserId(
        @Query("userid") userid: String
    ):Call<ChatMainDTO>

    @GET("/api/v1/chat/search/receive/send")
    fun chatTwoPeople(
        @Query("receive") receive: String,
        @Query("send") send: String
    ):Call<DMDTO>

//    @Headers("content-type:application/json; charset=utf-8")
    @POST("/api/v1/chat/insert")
    fun PostChatInsert(
        @Body params:ChatInsert
    ):Call<Any>
}

data class ChatInsert(
    val chatid: Int,
    val created: String,
    val id: Int,
    val message: String,
    val picture: String,
    val readmessage: Int,
    val receive: String,
    val send: String
)