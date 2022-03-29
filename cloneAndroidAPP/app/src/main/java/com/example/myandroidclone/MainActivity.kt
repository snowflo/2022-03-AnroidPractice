package com.example.myandroidclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

/*
# Error 목록

 1. unable to locate adb
  - https://www.geeksforgeeks.org/fix-unable-to-locate-adb-within-sdk-in-android-studio/
    → sdk\platform-tools 재설치 방법
  - 휴대폰 usb모드 USB테더링 → USB디버깅 설정 [해결: USB디버깅 키고, 연결을 이 모드로 해야 설정 가능]
 2. The minCompileSdk (31) specified in ~ 하면서 build가 안 되는 error
  - https://devshin93.tistory.com/116
    → Android API Level 31로 발생하는 문제로 추정
    → 1번 안은 안 됨, 2번 안으로 해결
 3. Manifest merger failed : Apps targeting Android 12 and higher are required to specify an explicit value for `android:exported` when the corresponding component has an intent filter defined.
  - https://ddolcat.tistory.com/1065
    → Android 12를 타겟팅 하여 발생하는 문제 (API Level 31)
    → Activity가 외부 응용프로그램과 통신 가능한지 True/False, Activity마다 설정 ▶ manifests file 참조
    → 인텐트 필터를 사용하는 경우 False는 안 됨, 인텐트 필터가 뭔지는 찾아볼 것
        ▶ https://www.charlezz.com/?p=859 
        ▶ 설명을 찾아보니 "앱 중에 전화와 연동, 주소록과 연동, ..."의 역할을 하는 것으로 추정
    → service와 receiver 도 마찬가지로 사용, service와 receiver가 뭔지는 찾아볼 것
        ▶ https://junghun0.github.io/2019/06/09/android-service/
            → service - 백그라운드에서 동작하는 프로세스, 화면이 없는 액티비티
        ▶ https://crazykim2.tistory.com/633
            → receiver - 특정 이벤트가 발생하면 Broadcast 해주는 기능
            → receiver를 검색하니 모두 BroadcastReceiver만 나옴, 이거인것 같음
 4. Please remove usages of `jcenter()` Maven repository from your build scripts and migrate your build to other Maven repositories.
  - https://okky.kr/article/1009091
    → Android에서 jcenter()가 서비스를 종료할 예정
    → 프로젝트 수준의 build.gradle에서 jcenter()를 지움
        ▶ 선택 사항으로 maven { url "https://maven.google.com" }를 적어도 되고, 안 적어도 됨
 5. 앱이 설치되지 않았습니다. 
  - https://ddolcat.tistory.com/1003
    → Android APP이 아이콘은 있는데 설치가 되지 않은 문제
    → 원인은 main이 되는 activity의 android:exported가 false
        ▶ 시작하는 Activity는 true로 지정하거나 android:exported를 제거해야한다.
          하지만 target 12를 대상으로는 android:exported가 필수기 때문에 true로 한다. 
 6. Activity에서 Layout 객체의 id를 인식하지 못하는 에러
  - https://yongku.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%8A%A4%ED%8A%9C%EB%94%94%EC%98%A4Android-Studio-findViewById
    → 최신 안드로이드 스튜디오는 kotlin-android-extensions를 자동으로 설정하지 않음 >> app수준의 build.gradle에 스스로 추가
        plugins {
            id 'com.android.application'
            id 'kotlin-android'
            id 'kotlin-android-extensions'
        }
    → findViewById를 사용
 7. 안드로이드에서 custom button의 색이 적용되지 않는 오류
  - https://curryyou.tistory.com/398
    → 기본 테마가 material >> app으로 변경
        ▶ material은 button을 material button으로 생성하고 이는 background를 무시
    → button을 material button이 아니라 app button으로 생성
 8. 안드로이드에서 스튜디오에서 Run이 뜨지 않는 문제
  -
    → 프로젝트 폴더를 잘못 찾은 문제 >> 프로젝트 폴더명, 폴더와 동일시 하지말 것
    → 폴더 위치를 잘못 찾으면 안 된다
 9. android.view.InflateException
  - https://youtu.be/cKvemtEP-Vw 의 댓글
    → Error inflating class com.google.android.material.textfield.TextInputLayout
    → theme를 Theme.Material3.DayNight.* 로 변경하여 사용
        ▶ 기존 >> Theme.MaterialComponents.Light.NoActionBar
        ▶ Material3로 해야되는듯
 */

class MainActivity : AppCompatActivity() {

    val TAG : String = "로그"

    // 액티비티 생성시 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 레이아웃 설정
        setContentView(R.layout.activity_main) // R >> Resource
        Log.d(TAG, "MainActivity - onCreate() called");
        
        // 로그인 버튼 뷰에 클릭 리스너 설정
//        login_btn.setOnClickListener(View.OnClickListener {
//            onLoginButtonClicked()  // 로그인 버튼 클릭시 발생하는 일을 함수 처리
//        })

        // lambda식
        login_btn.setOnClickListener { 
            // ( View.OnClickListener )를 생략
            onLoginButtonClicked()
        }

        arona_btn.setOnClickListener{
            onAronaButtonClicked()
        }

        someCallbackMethod(completion = {   // 비동기 처리
            Log.d(TAG, "MainActivity - 컴플레션 블럭 호출됨, it: $it");  // it은 it:String
            someCallbackMethod2 {
                Log.d(TAG, "MainActivity - 컴플레션 블럭 호출됨, it: $it");  // it은 it:String
                someCallbackMethod3 {
                    Log.d(TAG, "MainActivity - 컴플레션 블럭 호출됨, it: $it");  // it은 it:String
                }
            }
        })
    }

    override fun onStart() {
        super.onStart() // ctrl키로 기존의 위치로 갈 수 있음, ex: onStart >> AppCompatActivity
        Log.d(TAG, "MainActivity - onStart() called");
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity - onResume() called");
    }

    override fun onPause() {
        super.onPause()
        // 1. kotlin-android-extensions 사용
//        text_view.visibility = View.VISIBLE
//        text_view.setText("Hello Rabbit!!")
        // 2. findViewById 사용
        val text_view_by_id = findViewById<TextView>(R.id.text_view)
        text_view_by_id.visibility = View.VISIBLE
        text_view_by_id.setText("Hello  Kivotos!!")
        Log.d(TAG, "MainActivity - onPause() called");
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity - onStop() called");
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity - onDestroy() called");
    }

    // 로그인 함수
    fun onLoginButtonClicked(){
        Log.d(TAG, "MainActivity - onLoginButtonClicked() called");

        val intent = Intent(this, RelativeActivity::class.java)
        /*
        val >> 상수, 변하지 않는 값, Intent >> 안드로이드 화면 전환 / 매개변수 >> context
        Activity가 이동하며 stack처럼 쌓이는 개념
         */
        startActivity(intent) // Activity 실행
    }

    // arona 함수
    fun onAronaButtonClicked(){
        Log.d(TAG, "MainActivity - onAronaButtonClicked() called");
        
        val intent = Intent(this, PictureActivity::class.java)
        /*
        login과 arona를 합칠 방법을 모색할 필요가 있음
         */
        startActivity(intent)
    }

    // callback method
    fun someCallbackMethod(completion: (String) -> Unit){ // completion: () -> Unit >> 이게 무슨 문법이지?
        Log.d(TAG, "MainActivity - someCallbackMethod() called");

        // delay를 준다, 5000L >> 5초
        Handler().postDelayed({ // 컴플레션 블럭
                              completion("매개변수를 보내는 completion")    // kotlin String은 " "만 가능
        }, 5000L)
    }

    // 연쇄 callback Test, rxjava와 coroutine을 통해 사용이 가능
    fun someCallbackMethod2(completion: (String) -> Unit){ // completion: () -> Unit >> 이게 무슨 문법이지?
        Log.d(TAG, "MainActivity - someCallbackMethod() called");

        // delay를 준다, 5000L >> 5초
        Handler().postDelayed({ // 컴플레션 블럭
            completion("매개변수를 보내는 completion")    // kotlin String은 " "만 가능
        }, 5000L)
    }

    fun someCallbackMethod3(completion: (String) -> Unit){ // completion: () -> Unit >> 이게 무슨 문법이지?
        Log.d(TAG, "MainActivity - someCallbackMethod() called");

        // delay를 준다, 5000L >> 5초
        Handler().postDelayed({ // 컴플레션 블럭
            completion("매개변수를 보내는 completion")    // kotlin String은 " "만 가능
        }, 5000L)
    }
}

/*
# 공부 목록
 1. 안드로이드 생명 주기
  - https://developer.android.com/guide/components/activities/activity-lifecycle?hl=ko
    → 이미지 참조, 메모리 관리를 위함
 2. 라이브 템플릿 설정
  - File >> Settings >> Editor >> Live Templates >> AndroidLog >> logd 변경
    → Edit variables로 변수 설정
    → change 버튼으로 기본 언어 설정
  - reset 가능
 3. Logcat
  - view mode >> windows
 4. splash screen

 5. icon
  - 구글 검색 >> icon >> https://www.flaticon.com/kr/
  - drawable >> 우클릭 >> new >> Vector Asset >> Local, 주의: SVG,PSD만 가능
 6. 레이아웃
  - Linear
    → 방향 [Orientation] 중요 [수평/수직]
    → 어떤 것을 나열할 때 좋다
  - Relative
    → 관계를 의미
    → 내 위에 누구, 내 아래 누구, 내 옆에 누구ㅡ
  - Constraint
    → 강제 >> 고정
 7. figma
  - UI, 웹 베이스, 간단한 회원가입 후 사용
    → 다운로드 >> .fig 형태의 파일 >> figma 사이트에 그대로 올림
 8. manifests
  - Application을 관할
    → Activity를 관할
 9. 버튼 액션
  - 리스너 설정
  - xml에 onClick 설정
 10. 동기/비동기
  - Android Main
    → Main thread >> UI >> API 호출 >> 대기
  - 동기
    → API를 호출하고 대기하는 상황이다. 이를 동기라 함
  - 비동기 >> 컴플레션 블럭을 활용 >> closer 혹은 callback이라 함
    → 반면 기다리지 않고 나는 내 갈 길 간다. 이를 비동기라 함
    → 호출한 것이 알아서 돌아온다. >> 이를 callback이라 한다.
  - 일반적 환경
    → 웹 서버에 데이터 요청 >> API형태
        ▶ 보통 여러가지 단계를 거침
        ▶ 응답값에 따라서 여러 과정을 거침 >> ex) 로그인 정보 확인 >> 다음 정보 요청
    → rxjava와 coroutine을 통해 사용 가능
 11. Android API
  - Retrofit
    → client > [이 중간에 intercept로 처리 ]> server
    → 기본 매개 변수, logging 확인
 */