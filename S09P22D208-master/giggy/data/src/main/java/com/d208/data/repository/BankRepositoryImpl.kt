package com.d208.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.d208.data.mapper.BankMapper
import com.d208.data.repository.remote.datasource.BankDateSource
import com.d208.domain.model.DomainAnalysisResponse
import com.d208.domain.model.DomainTransaction
import com.d208.domain.repository.BankRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val bankDateSource: BankDateSource
) : BankRepository{
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun searchTransaction(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
        startDate: String,
        endDate: String
    ): MutableList<DomainTransaction>? {
       return BankMapper.searchTransactionMapper(bankDateSource.searchTransaction(remoteErrorEmitter, id, startDate, endDate))
    }

    override suspend fun searchMonths(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID
    ): MutableList<String>? {
        return BankMapper.searchMonths(bankDateSource.searchMonths(remoteErrorEmitter, id))
    }

    override suspend fun getAnalysis(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
        date: String
    ): MutableList<DomainAnalysisResponse>? {
        return BankMapper.getAnalysis(bankDateSource.getAnalysis(remoteErrorEmitter, id, date))
    }

    override suspend fun getRecentData(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
    ): Boolean? {
        return bankDateSource.getRecentData(remoteErrorEmitter, id)
    }

    override suspend fun updateCategory(
        remoteErrorEmitter: RemoteErrorEmitter,
        data: DomainTransaction
    ): Boolean? {
        return bankDateSource.updateCategory(remoteErrorEmitter, data)
    }
}