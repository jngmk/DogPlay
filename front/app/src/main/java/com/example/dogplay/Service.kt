package com.example.dogplay

import com.example.dogplay.ui.owner.RoomDTO
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.internal.ObjectConstructor
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Header
import java.text.DateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


data class HotelSerchDTO(
    @SerializedName("data")
    var data:ArrayList<Hotel>,
    @SerializedName("state")
    var state:String = ""
)
data class Hotel(var counstar: Int,
                 var address: String,
                 var star:Double,
                 var distance:Int,
                 var latitude:Double,
                 val userid:String,
                 var hashid:ArrayList<Int>,
                 var hotelnumber:String,
                 var countreview:Int,
                 var minprice:Int,
                 var contact:String,
                 var detail:ArrayList<ArrayList<String>>,
                 var hotelname:String,
                 var longitude:Double,
                 var info:String,
                 var hashtag:ArrayList<String>,
                 val picture: String
)



data class HotelDetailDTO(
    @SerializedName("data")
    var data: HotelDetailHash
)


data class HotelRoomDTO(
    @SerializedName("data")
    val data: ArrayList<RoomDetailData>,
    @SerializedName("state")
    val state: String
)

data class HotelNearByDTO(
    @SerializedName("data")
    val data: ArrayList<HotelInfoWithStarAndPrice>,
    @SerializedName("state")
    val state: String
)

data class HotelSearchByUserIdDTO(
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

data class HotelPicturesDTO(
    @SerializedName("data")
    val data: ArrayList<HotelPicture>,
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
    val HotelPicture:ArrayList<HotelPictureP>,
    val review: ArrayList<HotelReview>,
    val HotelRoom: ArrayList<HotelRoom>
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
    val hashid: ArrayList<String>,
    val hashtag: ArrayList<String>,
    val distance: Int,
    val minprice: Int
)

data class HotelRoom(
    val id: Int,
    val hotelnumber: String,
    val roomname: String,
    val price: Int,
    val minsize: Int,
    val maxsize: Int,
    val count:Int,
    val info: String
)

data class HotelRoomToPost(
    var id: Int = 0,
    var hotelnumber: String = "",
    var roomname: String= "",
    var price: Int = 0,
    var minsize: Int = 0,
    var maxsize: Int = 0,
    var count: Int = 0,
    var info: String = ""
)

data class HotelReview(
    val id: Int,
    val hotelnumber: String,
    val userid: String,
    val visitid:Int,
    val star: Double,
    val created: ArrayList<Int>,
    val content:String
)

data class HotelPictureP(
    val id: Int,
    val hotelnumber: String,
    val name: String,
    val picture: String
)

data class HotelInfo(
    val hotelnumber: String = "",
    val userid: String,
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
    val minprice: Int,
    val picture: String?
)

data class DMSend(
    @SerializedName("data")
    val data: ArrayList<Int>
)

data class HotelInfoToPost(
    var hotelnumber: String = "",
    var userid: String = "owner2",
    var hotelname: String= "",
    var latitude: Double = 37.0942,
    var longitude: Double = 127.0772,
    var address: String = "",
    var contact: String = "",
    var info: String = "",
    var detail: JsonObject = JsonObject()
)

data class HotelReturnData(
    val data: String,
    val state: String
)

data class RoomCountReturnData(
    val data: Int,
    val state: String
)

data class HotelPicture(
    var id: Int = 0,
    var hotelnumber: String = "",
    var name: String = "",
    var picture: String = ""
)

data class HotelHashToPost(
    var hashtag: Int = 0,
    var hotelnumber: String = "",
    var id: Int = 0
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
    val hotelnumber: String,
    val roomname: String,
    val price: Int,
    val minsize: Int,
    val maxsize: Int,
    val count: Int,
    val info: String
)

data class HashTag(
    val id: Int,
    val name: String
)

data class DMDTO(
    @SerializedName("data")
    val data:ArrayList<ChatIn>
)
data class ChatNew(
    @SerializedName("data")
    val data:Boolean
)

data class UserDTO(
    @SerializedName("data")
    val data: User,
    @SerializedName("state")
    val state: String
)

data class User(
    var userid: String = "",
    var nickname: String = "",
    var phone: String? = "",
    var picture: String? = "",
    var social: Int = 0,
    var admin: Int = 0
)


interface Service {
    @GET("/api/v1/user/searchbyuserid")
    fun getUserByUserId(
        @Query("userid") userid: String
    ): Call<UserDTO>

    @Headers("accept: application/json",
        "content-type: application/json")
    @PUT("/api/v1/user/update")
    fun putUser(
        @Body params: User
    ): Call<HotelReturnData>

    @GET("/api/v1/hotelstar/searchall")
    fun getRequest():Call<HotelSerchDTO>

    @GET("/api/v1/hotelstar/search/hotelnumber")
    fun searchHotelStar(
        @Query("hotelnumber") hotelnumber: String
    ):Call<HotelDTO>
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

    @GET("/api/v1/hotelstar/search/userid")
    fun getHotelSearchByUserId(
        @Query("userid") userid: String
    ): Call<HotelSearchByUserIdDTO>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/api/v1/hotel/insert")
    fun postHotelInfo(
        @Body params: HotelInfoToPost
    ):Call<HotelReturnData>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/api/v1/hotelroom/insert")
    fun postHotelRoomInfo(
        @Body params: HotelRoomToPost
    ):Call<HotelReturnData>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/api/v1/hotelpicture/insert")
    fun postHotelPictures(
        @Body params: HotelPicture
    ):Call<HotelReturnData>

    @GET("/api/v1/hotelpicture/searchbyhotel/name")
    fun getHotelPictures(
        @Query("hotelnumber") hotelnumber: String,
        @Query("name") name: String
    ): Call<HotelPicturesDTO>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/api/v1/hotelhash/insert")
    fun postHotelHash(
        @Body params: HotelHashToPost
    ):Call<HotelReturnData>

    @GET("/api/v1/hotelroom/search")
    fun searchRoomDetail(
        @Query("id") id:String
    ):Call<RoomDetailDTO>

    @GET("/api/v1/hotelroom/searchbyhotel")
    fun getRoomsSearchByHotel(
        @Query("hotelnumber") hotelnumber: String
    ):Call<HotelRoomDTO>

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
    ):Call<DMSend>

    @PUT("/api/v1/chat/update")
    fun ChatUpdate(
        @Body params: ChatIn
    ):Call<Any>

    @GET("/api/v1/chat/searchnew")
    fun ChatNew(
        @Query("receive") receive: String,
        @Query("send") send: String
    ):Call<ChatNew>

    @POST("/api/v1/cart/insert")
    fun CartInsert(
        @Body params: CartInsert
    ):Call<Any>

    @GET("/api/v1/cart/search/userid")
    fun searchCartById(
        @Query("userid") userid: String
    ):Call<responseCartDTO>

    @DELETE("/api/v1/cart/delete/userid")
    fun deleteCartById(
        @Query("userid") userid: String
    ):Call<Any>

    @GET("/api/v1/cart/totalprice")
    fun calcTotalPrice(
        @Query("userid") userid: String
    ):Call<calcTotalPrice>

    @POST("/api/v1/paid/kakaopay/ready")
    fun kakaoPay(
        @Body params:PayForm
    ):Call<kakaoReadyDTO>

//    @Headers("Authorization: KakaoAK a48ad79d9d765c639d119d3d093a1449",
//        "Content-type:application/x-www-form-urlencoded;charset=utf-8")
//    @FormUrlEncoded
//    @POST("/v1/payment/ready")
//    fun kakaoPay(
//        @Field("cid") cid: String,
//        @Field("partner_order_id") partner_order_id:String,
//        @Field("partner_user_id") partner_user_id:String,
//        @Field("item_name")item_name:String,
//        @Field("quantity") quantity:Int,
//        @Field("total_amount")total_amount:Int,
//        @Field("tax_free_amount") tax_free_amount:Int,
//        @Field("approval_url")approval_url:String,
//        @Field("fail_url") fail_url:String,
//        @Field("cancel_url") cancel_url:String
//    ):Call<Any>
    @GET("/api/v1/review/searchbyhotel")
    fun searchByHotel(
        @Query("hotelnumber") hotelnumber: String
    ):Call<SearchReviewDTO>

    @GET("/api/v1/response/search/review")
    fun searchByReview(
        @Query("reviewid") reviewid:Int
    ):Call<SearchResponseDTO>

    @POST("/api/v1/paid/insert")
    fun insertPaid(
        @Body params: InsertPaid
    ):Call<PaidResponse>

    @GET("/api/v1/reservation/count/hotel/room/start/finish/")
    fun getReservationRoomCount(
        @Query("hotelnumber") hotelnumber: String,
        @Query("roomname") roomname: String,
        @Query("startdate") startdate: LocalDateTime,
        @Query("finishdate") finishdate: LocalDateTime
    ):Call<RoomCountReturnData>

    @GET("/api/v1/reservation/search/hotel/room/start/finish/")
    fun getReservationRoomDetail(
        @Query("hotelnumber") hotelnumber: String,
        @Query("roomname") roomname: String,
        @Query("startdate") startdate: LocalDateTime,
        @Query("finishdate") finishdate: LocalDateTime
    ):Call<ReservationDTO>

    @POST("/api/v1/reservation/insert")
    fun insertReservation(
        @Body params:InsertReservation
    ):Call<Any>

    @GET("/api/v1/reservation/searchbyuserid")
    fun searchReservationByUser(
        @Query("userid") userid: String
    ):Call<ReservationDTO>

    @POST("/api/v1/review/insert")
    fun insertReview(
        @Body params: InsertReview
    ):Call<Any>

    @PUT("/api/v1/reservation/update")
    fun updateReservation(
        @Body params: InsertReservation
    ):Call<Any>
}
data class InsertReview(
    val content: String,
    val created: String,
    val hotelnumber:String,
    val id: Int,
    val star: Double,
    val userid: String,
    val visitid: Int
)

data class InsertReservation(
    val count: Int,
    val dog: String,
    val finishdate: String,
    val hotelnumber: String,
    val id:Int,
    val paidid: Int,
    val phone:String,
    val roomname: String,
    val startdate: String,
    val userid: String,
    val visitid: Int
)

data class PaidResponse(
    @SerializedName("data")
    val data:Int
)

data class ReservationDTO(
    @SerializedName("data")
    val data: ArrayList<Reservation>,
    @SerializedName("state")
    val state: String
)

data class Reservation(
    var id: Int = 0,
    var paidid: Int = 0,
    var hotelnumber: String = "",
    var userid: String = "",
    var dog: String = "",
    var roomname: String = "",
    var startdate: ArrayList<Int> = ArrayList(),
    var finishdate: ArrayList<Int> = ArrayList(),
    var count: Int = 0,
    var visit: Int = 0,
    var phone: String? = ""
)

data class InsertPaid(
    val aid:String,
    val cancel_amount:Int,
    val cancel_tax_free_amount:Int,
    val cid:String,
    val id:Int,
    val partner_order_id: String,
    val pg_token:String,
    val tid: String,
    val userid: String
)

data class kakaoReadyDTO(
    val data:kakaoReady
)

data class HotelDTO(
    @SerializedName("data")
    val data: Hotel
)

data class kakaoReady(
    val tid:String,
    val tms_result:Boolean,
    val next_redirect_app_url:String,
    val next_redirect_mobile_url:String,
    val next_redirect_pc_url:String,
    val android_app_scheme:String,
    val ios_app_scheme:String,
    val created_at:String
)

data class SearchResponseDTO(
    val data:SearchResponse
)

data class SearchResponse(
    val id: Int,
    val reviewid: Int,
    val userid: String,
    val heart:Int,
    val content:String,
    val created: ArrayList<Int>
)


data class SearchReviewDTO(
    val data:ArrayList<SearchReview>
)

data class SearchReview(
    val id: Int,
    val hotelnumber: String,
    val userid: String,
    val visitid: Int,
    val star: Double,
    val created: ArrayList<Int>,
    val content: String
)

data class PayForm(
    val cid:String,
    val partner_order_id:String,
    val partner_user_id:String,
    val item_name:String,
    val quantity:Int,
    val total_amount:Int,
    val tax_free_amount:Int,
    val approval_url:String,
    val cancel_url:String,
    val fail_url:String
)

data class calcTotalPrice(
    @SerializedName("data")
    val data:Int
)

data class responseCartDTO(
    @SerializedName("data")
    val data:ArrayList<responseCart>
)
data class responseCart(
    val id: Int,
    val userid: String,
    val hotelnumber: String,
    val roomname: String,
    val price: Int
)

data class CartInsert(
    val hotelnumber: String,
    val id: Int,
    val price: Int,
    val roomname: String,
    val userid: String
)

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