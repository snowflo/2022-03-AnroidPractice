package com.example.myandroidclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myandroidclone.utils.Constants
import com.example.myandroidclone.utils.SEARCH_TYPE
import com.example.myandroidclone.utils.onMyTextChanged
import kotlinx.android.synthetic.main.activity_picture.*
import kotlinx.android.synthetic.main.layout_button_search.*

class PictureActivity : AppCompatActivity() {

    private var currentSearchType : SEARCH_TYPE = SEARCH_TYPE.PHOTO
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        Log.d(Constants.TAG, "PictureActivity - onCreate() called");
        
        /*
            ex 
            - https://www.android--code.com/2018/02/android-kotlin-radiogroup-and.html
                → lambda식이 아닌 방식으로 하는 방법을 찾아볼 것 [공부를 위해]
         */
        // 라디오 그룹 이벤트
        search_term_radio_group.setOnCheckedChangeListener { _, checkedId ->
            // 만약 매개변수를 받아와 사용하지 않는다면 _로 체크

            // switch문
            when(checkedId){
                R.id.photo_search_radio_button -> {
                    Log.d(Constants.TAG, "사진검색 버튼 클릭");
                    search_term_text_layout.hint = "사진검색"
                    search_term_text_layout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_photo_library_24)
                            // resources >> get resource 하는 부분
                    this.currentSearchType = SEARCH_TYPE.PHOTO
                }
                R.id.user_search_radio_button -> {
                    Log.d(Constants.TAG, "사용자검색 버튼 클릭");
                    search_term_text_layout.hint = "사용자검색"
                    search_term_text_layout.startIconDrawable = resources.getDrawable(R.drawable.ic_baseline_person_24)
                    // resources >> get resource 하는 부분
                    this.currentSearchType = SEARCH_TYPE.USER
                }
            }
        }
        // 텍스트 이벤트 >> 텍스트가 변경 되었을 때
        search_term_edit_text.onMyTextChanged{
            // 너무 길어서 utils.Extensions로 뺌

            // 입력된 글자가 하나라도 있다면 
            if(it.toString().count() > 0){
                frame_search_button.visibility = View.VISIBLE
                search_term_text_layout.helperText = " "
                // 스크롤 뷰를 올린다
                picture_scoll_view.scrollTo(0, 200)
            } else {
                frame_search_button.visibility = View.INVISIBLE
            }

            if(it.toString().count() == 12){
                Log.d(Constants.TAG, "에러 띄우기")
                Toast.makeText(this, "검색어는 12자 까지만 입력 가능합니다. ", Toast.LENGTH_SHORT)
                /*
                    Toast >> 글자 띄우기
                        → this >> 이 액티비티, String >> 메시지, Toast.LENGTH_SHORT >> 띄우는 시간
                 */
            }
        }

        btn_search.setOnClickListener {
            Log.d(Constants.TAG, "검색버튼이 클릭되었다. \n currentSearchType : $currentSearchType"); // $로 " "내에서 변수로 사용
            this.handleSearchButtonUi() // this >> 이 액티비티의 . >> 함수를 사용
            Toast.makeText(this, "검색 버튼 눌림", Toast.LENGTH_SHORT)
        }
    } // onCreate

    private fun handleSearchButtonUi(){
        Log.d(Constants.TAG, "PictureActivity - handleSearchButtonUi() called");
        btn_progress.visibility = View.VISIBLE

        btn_search.text = ""

        Handler().postDelayed({     // Handler().postDelayed >> { } [function], int [time]
            Log.d(Constants.TAG, "handleSearchButtonUi 호출됨");
            btn_progress.visibility = View.INVISIBLE
            btn_search.text = "검색"
        }, 1500)
    }
}