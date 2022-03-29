package com.example.myandroidclone.retrofit

import android.util.Log
import com.example.myandroidclone.utils.Constants.TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// singleton >> object : 싱글톤으로 하겠다
object RetrofitClient {
    // retrofit client 선언
    private var retrofitClient : Retrofit? = null
//    private lateinit var retrofitClient : Retrofit    // 늦은 초기화 >> 로도 할 수 있다
    
    // retrofit client 가져오기
    fun getClient(baseUrl: String) : Retrofit? { // fun 함수명 (매개변수) : 반환형
        Log.d(TAG, "RetrofitClient - getClient() called")

        if(retrofitClient == null){

            // design pattern >> build pattern (생성 패턴)
                // retrofitClient가 없다면 생성하여 반환
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)   // 데이터를 받아올 baseUrl
                .addConverterFactory(GsonConverterFactory.create()) // json ↔ object 변경 팩토리 생성
                .build()
        }
        return retrofitClient
    }
}