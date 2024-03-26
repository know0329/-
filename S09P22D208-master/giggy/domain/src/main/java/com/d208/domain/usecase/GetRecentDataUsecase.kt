package com.d208.domain.usecase

import com.d208.domain.repository.BankRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class GetRecentDataUsecase @Inject constructor(
    private val bankRepository: BankRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, id : UUID) = bankRepository.getRecentData(remoteErrorEmitter, id)
}