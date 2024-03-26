package com.d208.domain.usecase

import com.d208.domain.model.DomainUser
import com.d208.domain.model.SignUpUser
import com.d208.domain.repository.MainRepository
import com.d208.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, data : SignUpUser) = mainRepository.signUp(remoteErrorEmitter, data)
}