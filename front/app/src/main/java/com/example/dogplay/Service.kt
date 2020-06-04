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
                 var hashtag:ArrayList<String>
)



data class HotelDetailDTO(
    @SerializedName("data")
    var data: HotelDetailHash
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
    val HotelPicture:ArrayList<HotelPicture>,
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

data class HotelReview(
    val id: Int,
    val hotelnumber: String,
    val userid: String,
    val visitid:Int,
    val star: Int,
    val created: ArrayList<Int>,
    val content:String
)

data class HotelPicture(
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
    val minprice: Int
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
data class ChatNew(
    @SerializedName("data")
    val data:Boolean
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

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("/api/v1/hotel/insert")
    fun postHotelInfo(
        @Body params: HotelInfoToPost
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

    @Headers("Authorization: KakaoAK xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
        "Content-type:application/x-www-form-urlencoded;charset=utf-8")
    @POST("/v1/payment/ready")
    fun kakaoPay(
        @Body params:PayForm
    ):Call<Any>

//    @Headers("Authorization: KakaoAK xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
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
}

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