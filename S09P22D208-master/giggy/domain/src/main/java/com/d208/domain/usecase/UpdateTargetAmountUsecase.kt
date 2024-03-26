package com.d208.domain.usecase

import com.d208.domain.repository.MainRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class UpdateTargetAmountUsecase @Inject constructor(
     private val mainRepository: MainRepository
){
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, id : UUID, targetAmount : Int) = mainRepository.updateTargetAmount(remoteErrorEmitter, id, targetAmount)
}