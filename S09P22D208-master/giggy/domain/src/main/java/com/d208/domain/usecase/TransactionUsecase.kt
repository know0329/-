package com.d208.domain.usecase

import com.d208.domain.repository.BankRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class TransactionUsecase @Inject constructor(
    private val bankRepository: BankRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, id : UUID, startDate : String, endDate : String) = bankRepository.searchTransaction(remoteErrorEmitter, id, startDate, endDate)
}