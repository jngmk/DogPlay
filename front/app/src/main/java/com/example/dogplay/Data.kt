package com.example.dogplay

data class Hotel(var title: String, var img:String, var eval:String, var review:String, var address:String, var price:String)
data class Dog(var img:String)
data class Room(var title:String)
data class Info(var title:String, var sub:String)

object Supplier{

    var hotels = listOf<Hotel>(
        Hotel("장판 뜯어 차린 카페", "@drawable/dog","4.7/5.0", "후기 402 건", "서울 특별시 관악구 신림동", "30000원"),
        Hotel("퍼피퍼피 하우스", "@drawable/dog2","4.1/5.0", "후기 221 건", "인천광역시 남구 용현동", "24000원"),
        Hotel("참편한 개집", "@drawable/dog3","4.9/5.0", "후기 983 건", "서울특별시 관악구 낙성대동", "45000원")
    )
    var dogs = listOf<Dog>(
        Dog("멍멍"),
        Dog("멍멍"),
        Dog("멍멍")
    )

    var rooms = listOf<Room>(
        Room("좋은 방"),
        Room("나쁜 방"),
        Room("이상한 방")
    )

    var infos = listOf<Info>(
        Info("주의해야할 점!", "잘 사용해주시고 \n또 잘 사용해주세요!"),
        Info("해야해야할 점!", "잘 사용해주시고 \n또 시러요!"),
        Info("몰라몰라 점!", "잘 사용해주시고 \n또 잘 사용해주세요!"),
        Info("장기숙박 점!", "잘 사용해주시고 \n또 잘 사용해주세요! \n서비스 숙박 35일"),
        Info("주의해야할 점!", "잘 사용해주시고 \n또 잘 사용해주세요! \n안가르쳐줘")
    )
}

