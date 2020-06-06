package com.example.dogplay.ui.owner

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

data class RereviewDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
)

data class HotelDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
)

data class ReservationDTO(
    @SerializedName("data")
    var data:Array<HashMap<String,Any>>
)

data class RoomDeleteDTO(
    var id: Int? = null
)

data class RoomDTO(
    @SerializedName("data")
    var data:Array<HashMap<String,Any>>
)

data class EditHotelDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
)
data class EditRoomDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
)
data class TokenDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
)

data class AllHotelDTO(
    @SerializedName("data")
    var data:Array<HashMap<String,Any>>,
    @SerializedName("state")
    var state:String = ""
)

data class TokenToDTO(
    var token: String
)

data class UserInfoDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
)


interface EditService {
    @GET("/api/v1/user/searchbyuserid")
    fun getUserInfo(
        @Query("userid") userid: String
    ):Call<UserInfoDTO>


    @Headers("content-type: application/json")
    @POST("/api/v1/user/kakaologin")
    fun kakaoLogin(
        @Body params: String
    ):Call<TokenDTO>

    @POST("/api/v1/response/insert")
    fun reReview(
        @Field("reviewid") reviewid: Int,
        @Field("userid") userid:String,
        @Field("content") content: String,
        @Field("heart") heart: Int
    ):Call<RereviewDTO>

    @GET("/api/v1/hotelroom/searchbyroom/hotelnumber")
    fun searchHotelRoomDetail(
        @Query("hotelnumber") hotelnumber: String,
        @Query("roomname") roomname: String
    ):Call<EditRoomDTO>

    @GET("/api/v1/hotel/search/hotelnumber")
    fun searchHotelDetail(
        @Query("hotelnumber") hotelnumber: String
    ):Call<HotelDTO>

    @GET("/api/v1/review/searchbyhotel")
    fun searchReservation(
        @Query("hotelnumber") hotelnumber: String
    ):Call<ReservationDTO>

    @GET("/api/v1/hotel/searchall")
    fun searchAllHotel():Call<AllHotelDTO>

    @FormUrlEncoded
    @PUT("/api/v1/hotel/update")
    fun editHotelDetail(
        @Field("address") address: String,
        @Field("contact") contact: Char,
        @Field("detail") detail:HashMap<String,String>,
        @Field("hotelname") hotelname:String,
        @Field("hotelnumber") hotelnumber:Number,
        @Field("info") info:String,
        @Field("latitude") latitude:Double,
        @Field("longitude") longitude:Double,
        @Field("userid") userid:String
    ):Call<EditHotelDTO>

    @GET("/api/v1/hotelroom/searchbyhotel")
    fun searchRoomDetail(
        @Query("hotelnumber") hotelnumber: String
    ):Call<RoomDTO>


    @DELETE("/api/v1/hotelroom/delete")
    fun deleteRoom(
        @Field("id") id: Int
    ):Call<RoomDeleteDTO>

}