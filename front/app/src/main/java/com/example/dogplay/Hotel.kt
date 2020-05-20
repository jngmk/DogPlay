package com.example.dogplay

data class Hotel(var title: String, var img:String, var eval:String, var review:String, var address:String, var price:String)

object Supplier{

    var hotels = listOf<Hotel>(
        Hotel("장판 뜯어 차린 카페", "@drawable/dog","4.7/5.0", "후기 402 건", "서울 특별시 관악구 신림동", "30000원"),
        Hotel("퍼피퍼피 하우스", "@drawable/dog2","4.1/5.0", "후기 221 건", "인천광역시 남구 용현동", "24000원"),
        Hotel("참편한 개집", "@drawable/dog3","4.9/5.0", "후기 983 건", "서울특별시 관악구 낙성대동", "45000원")
    )
}