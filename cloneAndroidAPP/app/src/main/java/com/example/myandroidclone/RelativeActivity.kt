package com.example.myandroidclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class RelativeActivity : AppCompatActivity() {
    val TAG = "로그"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relative)

        // onClick >> xml에서 이벤트 설정
    }

    fun onBackButtonClicked(view: View){
        Log.d(TAG, "RelativeActivity - onBackButtonClicked() called");
        finish()
    }
}