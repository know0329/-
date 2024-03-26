package com.d208.giggy.di


import com.d208.data.remote.api.BankApi
import com.d208.data.remote.api.PostApi
import com.d208.data.remote.api.RankApi
import com.d208.data.remote.api.UserApi
import com.d208.giggy.utils.Utils.BASE_URL
import com.google.gson.GsonBuilder
import com.ssafy.template.config.XAccessTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    //okHttp 의존성 주입 (아래 retrofit 의존성 주입에 사용)
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
//            .addInterceptor(getLoggingInterceptor())
//            .addInterceptor(AddCookiesInterceptor())  //쿠키 전송
            .build()
    }

    @Provides
    @Singleton
    //gson 의존성 주입 (아래 retrofit 의존성 주입에 사용)
    fun provideConverterFactory(): GsonConverterFactory {

        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    //retrofit 의존성 주입 (아래 LoveCalculatorApi 의존성 주입에 사용)
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
//            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    //LoveCalculatorApi interface 의존성 주입
    fun provideUserApiService(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideBankApiService(retrofit: Retrofit) : BankApi{
        return retrofit.create(BankApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostApiService(retrofit: Retrofit) : PostApi{
        return retrofit.create(PostApi::class.java)
    }
    @Provides
    @Singleton
    fun provideRankApiService(retrofit: Retrofit) : RankApi{
        return retrofit.create(RankApi::class.java)
    }


}