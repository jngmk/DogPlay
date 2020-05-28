package com.example.dogplay

data class Hotel(var title: String, var img:String, var eval:String, var review:String, var address:String, var price:String)
//data class Hotel(var address: String, var contact:String, var detail:String, var hashid:String, var hotelname:String, var hotelnumber:Long, var info:String, var latitude:Double, var longitude:Double, var userid:String)
data class Dog(var img:String)
data class Room(var id:String)
data class Info(var title:String, var sub:String)
data class Chatting(var id:String, var chats:Array<String>)

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

    var chattings = listOf<Chatting>(
        Chatting("박권응", arrayOf("안녕하세요","좋은아침입니다","안녕히가세요")),
        Chatting("양희철", arrayOf("왜요", "뭐요", "싫어요")),
        Chatting("김현화", arrayOf("글쎼요", "모르겠는데요", "귀찮아요")),
        Chatting("김정", arrayOf("바빠요", "잠만요", "몰라요")),
        Chatting("양희철", arrayOf("왜요", "뭐요", "싫어요")),
        Chatting("김현화", arrayOf("글쎼요", "모르겠는데요", "귀찮아요")),
        Chatting("김정", arrayOf("바빠요", "잠만요", "몰라요")),
        Chatting("양희철", arrayOf("왜요", "뭐요", "싫어요")),
        Chatting("김현화", arrayOf("글쎼요", "모르겠는데요", "귀찮아요")),
        Chatting("김정", arrayOf("바빠요", "잠만요", "몰라요"))
    )
}

