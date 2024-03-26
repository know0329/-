package com.d208.data.repository.remote.datasourceimpl

import com.d208.data.remote.api.BankApi
import com.d208.data.remote.model.AnalysisRequest
import com.d208.data.remote.model.AnalysisResponse
import com.d208.data.remote.model.TransactionRequest
import com.d208.data.remote.model.TransactionResponse
import com.d208.data.repository.remote.datasource.BankDateSource
import com.d208.data.utils.base.BaseDataSource
import com.d208.domain.model.DomainTransaction
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class BankDateSourceImpl @Inject constructor(
    private val bankApi : BankApi,
) : BaseDataSource(), BankDateSource{
    override suspend fun searchTransaction(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
        startDate: String,
        endDate: String
    ): List<TransactionResponse>? {
        return safeApiCall(remoteErrorEmitter){
            val data = TransactionRequest(id, startDate, endDate)
            bankApi.searchTransaction(data)?.body()
        }

    }

    override suspend fun searchMonths(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID
    ): MutableList<String>? {
        return safeApiCall(remoteErrorEmitter){
            bankApi.searchMonths(id)?.body()
        }
    }

    override suspend fun getAnalysis(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
        date: String
    ): MutableList<AnalysisResponse>? {
        return safeApiCall(remoteErrorEmitter){
            val request = AnalysisRequest(id, date)
            bankApi.getAnalysis(request)?.body()
        }
    }

    override suspend fun getRecentData(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
    ): Boolean? {
        return safeApiCall(remoteErrorEmitter){
            bankApi.getRecentData(id)?.body()
        }
    }

    override suspend fun updateCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        data: DomainTransaction
    ): Boolean? {
        return safeApiCall(remoteErrorEmitter){
            bankApi.updateCategory(data)?.body()
        }
    }
}