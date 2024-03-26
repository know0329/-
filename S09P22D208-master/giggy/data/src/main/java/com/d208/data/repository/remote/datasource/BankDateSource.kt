package com.d208.data.repository.remote.datasource

import com.d208.data.remote.model.AnalysisResponse
import com.d208.data.remote.model.TransactionResponse
import com.d208.domain.model.DomainTransaction
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID

interface BankDateSource {

    suspend fun searchTransaction(
        remoteErrorEmitter: RemoteErrorEmitter,
        id : UUID,
        startDate : String,
        endDate : String,
    ) : List<TransactionResponse>?

    suspend fun searchMonths(
        remoteErrorEmitter: RemoteErrorEmitter,
        id : UUID,
    ) : MutableList<String>?

    suspend fun getAnalysis(
        remoteErrorEmitter: RemoteErrorEmitter,
        id : UUID,
        date : String,
    ) : MutableList<AnalysisResponse>?

    suspend fun getRecentData(
        remoteErrorEmitter: RemoteErrorEmitter,
        id : UUID
    ) : Boolean?

    suspend fun updateCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        data : DomainTransaction
    ) : Boolean?
}