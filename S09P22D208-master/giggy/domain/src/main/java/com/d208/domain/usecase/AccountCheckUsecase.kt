package com.d208.domain.usecase

import com.d208.domain.repository.MainRepository
import com.d208.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class AccountCheckUsecase @Inject constructor(
    private val mainRepository: MainRepository
){
   suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, accountNumber : String, fcmToken : String, birthday : String) = mainRepository.accountAuth(remoteErrorEmitter, accountNumber, fcmToken, birthday)
}