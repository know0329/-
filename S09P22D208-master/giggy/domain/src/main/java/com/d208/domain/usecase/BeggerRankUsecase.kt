package com.d208.domain.usecase

import com.d208.domain.repository.RankRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class BeggerRankUsecase @Inject constructor(
    private val rankRepository: RankRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter) = rankRepository.getBeggerRank(remoteErrorEmitter)

    suspend fun executeMyBeggerRank(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID)  =rankRepository.getMyBeggerRank(remoteErrorEmitter, userId)
}