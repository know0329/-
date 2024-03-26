package com.d208.domain.usecase

import com.d208.domain.repository.RankRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class GameRankUsecase @Inject constructor(
    private val rankRepository: RankRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter) = rankRepository.getGameRank(remoteErrorEmitter)

    suspend fun executeMyGameRank(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID) = rankRepository.getMyGameRank(remoteErrorEmitter, userId)
}