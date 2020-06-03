package com.example.dogplay.ui.owner

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

data class HotelDTO(
    @SerializedName("data")
    var data:HashMap<String,Any>
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


data class AllHotelDTO(
    @SerializedName("data")
    var data:Array<HashMap<String,Any>>,
    @SerializedName("state")
    var state:String = ""
)

interface EditService {
    @GET("/api/v1/hotelroom/searchbyroom/hotelnumber")
    fun searchHotelRoomDetail(
        @Query("hotelnumber") hotelnumber: String,
        @Query("roomname") roomname: String
    ):Call<EditRoomDTO>

    @GET("/api/v1/hotel/search/hotelnumber")
    fun searchHotelDetail(
        @Query("hotelnumber") hotelnumber: String
    ):Call<HotelDTO>

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