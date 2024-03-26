package com.d208.domain.usecase

import com.d208.domain.model.DomainTransaction
import com.d208.domain.repository.BankRepository
import com.d208.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class UpdateCategoryUsecase @Inject constructor(
    private val bankRepository: BankRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, data : DomainTransaction) = bankRepository.updateCategory(remoteErrorEmitter, data)
}