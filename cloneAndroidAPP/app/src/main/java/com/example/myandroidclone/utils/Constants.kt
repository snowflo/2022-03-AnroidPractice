package com.example.myandroidclone.utils

object Constants {
    const val TAG : String = "로그"
}   // object

enum class SEARCH_TYPE {
    PHOTO,
    USER
}   // 사진, 사용자 둘 중 하나를 search >> 후에 api 호출시 사용

/*
    enum class
    - https://believecom.tistory.com/707
        → 개발시 상수를 열거하기 위해 사용하는 클래스 >> 상수를 집합으로 관리해 가독성이 상승
 */