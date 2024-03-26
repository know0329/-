package com.d208.data.remote.api

import com.d208.data.remote.model.AnalysisRequest
import com.d208.data.remote.model.AnalysisResponse
import com.d208.data.remote.model.TransactionRequest
import com.d208.data.remote.model.TransactionResponse
import com.d208.domain.model.DomainTransaction
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.UUID

interface BankApi {

    @POST("app/account-history/app")
    suspend fun searchTransaction(@Body data : TransactionRequest) : Response<List<TransactionResponse>>

    @POST("app/account-history/month")
    suspend fun searchMonths(@Body id : UUID) : Response<MutableList<String>>

    @POST("app/account-history/analysis")
    suspend fun getAnalysis(@Body data : AnalysisRequest) : Response<MutableList<AnalysisResponse>>

    // 은행에 요청해서 최신 정보 받아오기
    @POST("app/account-history/bank")
    suspend fun getRecentData(@Body data : UUID) : Response<Boolean>

    @PUT("app/account-history/category")
    suspend fun updateCategory(@Body data : DomainTransaction) : Response<Boolean>

}