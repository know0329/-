package com.d208.domain.usecase

import com.d208.domain.model.DomainUser
import com.d208.domain.repository.MainRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, data : DomainUser) = mainRepository.getUserData(remoteErrorEmitter, data)
}