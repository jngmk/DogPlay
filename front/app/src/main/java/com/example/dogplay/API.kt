package com.example.dogplay

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class API{
    companion object{
        val API_BASE_URL = "http://k02a4021.p.ssafy.io:8080"
        val kakaoPay = "https://kapi.kakao.com"
        fun server(): Service? {
//            val retrofit = Retrofit.Builder()
//                .baseUrl(API_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//             return retrofit.create(Service::class.java)
             val retrofit = Retrofit.Builder()
                 .baseUrl(API_BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build()
            return retrofit.create(Service::class.java)
        }
        fun kserver():Service?{
            val retrofit = Retrofit.Builder()
                .baseUrl(kakaoPay)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(Service::class.java)
        }

    }
}