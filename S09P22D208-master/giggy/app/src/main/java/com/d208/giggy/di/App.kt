package com.d208.giggy.di

import android.app.Application
import com.d208.giggy.R
import com.d208.giggy.utils.SharedPreferencesUtil
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        private lateinit var application: App
        fun getInstance() : App = application
        lateinit var sharedPreferences: SharedPreferencesUtil
        const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCE"
    }


    override fun onCreate(){
        super.onCreate()
        application = this

        FirebaseApp.initializeApp(this)

        //Shared
        sharedPreferences = SharedPreferencesUtil(applicationContext)

        val appKey = getString(R.string.kakao_native_key)
        KakaoSdk.init(this, "$appKey")
    }
}