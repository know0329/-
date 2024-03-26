package com.d208.domain.repository

import com.d208.domain.model.DomainAnalysisResponse
import com.d208.domain.model.DomainTransaction
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID

interface BankRepository {

    suspend fun searchTransaction(remoteErrorEmitter: RemoteErrorEmitter, id : UUID, startDate : String, endDate : String ): MutableList<DomainTransaction>?

    suspend fun searchMonths(remoteErrorEmitter: RemoteErrorEmitter, id : UUID) : MutableList<String>?

    suspend fun getAnalysis(remoteErrorEmitter: RemoteErrorEmitter, id : UUID, date : String) : MutableList<DomainAnalysisResponse>?

    suspend fun getRecentData(remoteErrorEmitter: RemoteErrorEmitter, id : UUID) : Boolean?

    suspend fun updateCategory(remoteErrorEmitter: RemoteErrorEmitter, data : DomainTransaction) : Boolean?

}