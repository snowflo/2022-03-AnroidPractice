package com.example.myandroidclone.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

// EditText에 대한 Extension
fun EditText.onMyTextChanged(completion : (Editable?) -> Unit){ // completion >> 비동기 처리 - https://www.youtube.com/watch?v=PFfez5TBP2M&ab_channel=%EA%B0%9C%EB%B0%9C%ED%95%98%EB%8A%94%EC%A0%95%EB%8C%80%EB%A6%AC
    // 반환을 할 것이기 때문에 completion : (Editable?)로 등록
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {  // 변경 전

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { // 변경 중

        }

        override fun afterTextChanged(editable: Editable?) { // 변경 후
            completion(editable)
            // 반환을 하지 않을거면 그냥 이렇게 써도 무방함
        }

    })
}