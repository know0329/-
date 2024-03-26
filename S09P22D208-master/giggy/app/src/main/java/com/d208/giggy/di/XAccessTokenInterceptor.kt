package com.ssafy.template.config

import android.util.Log
import com.d208.giggy.di.App.Companion.sharedPreferences
import com.d208.giggy.utils.Utils.ACCESS_TOKEN

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sharedPreferences.getString(ACCESS_TOKEN)
        if (jwtToken != null) {
            Log.d("헤더", "intercept: $jwtToken")
            builder.addHeader("Authorization", jwtToken)
        }
        return chain.proceed(builder.build())
    }
}