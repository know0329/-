package com.d208.domain.usecase

import com.d208.domain.repository.MainRepository
import com.d208.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class LoginUsecase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, accessToken : String, refreshToken : String, fcmToken : String) = mainRepository.login(remoteErrorEmitter, accessToken, refreshToken, fcmToken)
}