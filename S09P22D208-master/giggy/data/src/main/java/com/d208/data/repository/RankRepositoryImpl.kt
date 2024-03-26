package com.d208.data.repository

import com.d208.data.mapper.RankMapper
import com.d208.data.repository.remote.datasource.RankDataSource
import com.d208.domain.model.DomainBeggerRank
import com.d208.domain.model.DomainGameRank
import com.d208.domain.model.DomainMyBegger
import com.d208.domain.model.DomainMyGame
import com.d208.domain.repository.RankRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class RankRepositoryImpl @Inject constructor(
    private val rankDataSource: RankDataSource
) : RankRepository{
    override suspend fun getBeggerRank(remoteErrorEmitter: RemoteErrorEmitter): List<DomainBeggerRank>? {
        return RankMapper.getBeggerRank(rankDataSource.getBeggerRank(remoteErrorEmitter))
    }

    override suspend fun getMyBeggerRank(
        remoteErrorEmitter: RemoteErrorEmitter,
        userId: UUID
    ): DomainMyBegger? {
       return RankMapper.getMyBeggerRank(rankDataSource.getMyBeggerRank(remoteErrorEmitter, userId))
    }

    override suspend fun getGameRank(remoteErrorEmitter: RemoteErrorEmitter): List<DomainGameRank>? {
        return RankMapper.getGameRank(rankDataSource.getGameRank(remoteErrorEmitter))
    }

    override suspend fun getMyGameRank(
        remoteErrorEmitter: RemoteErrorEmitter,
        userId: UUID
    ): DomainMyGame? {
        return RankMapper.getMyGameRank(rankDataSource.getMyGameRank(remoteErrorEmitter, userId))
    }


}