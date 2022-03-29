package com.example.myandroidclone.retrofit

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    /*
        - request 요청
            → https://unsplash.com/documentation 23:34 부터 다시 시작
            → https://unsplash.com/oauth/applications 사이트 에러로 강의를 보면서 의미만 파악하기로 우선...
     */
    @GET("/search/photos")  // GET의 url을 상수처리
    fun searchPhotos(@Query("query") searchTerm: String) : Call<JsonElement>  // 매개변수를 query 형으로 넣겠다
        // https://www.unsplash.com/search/photos/?query="매개변수값"
        // baseurl                 @GET          @Query

    @GET("/search/users")
    fun searchUsers(@Query("query") searchTerm: String) : Call<JsonElement>
}