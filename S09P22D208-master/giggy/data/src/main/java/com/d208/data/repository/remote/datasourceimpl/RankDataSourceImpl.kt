package com.d208.data.repository.remote.datasourceimpl

import com.d208.data.remote.api.RankApi
import com.d208.data.remote.model.BeggerRankResponse
import com.d208.data.remote.model.GameRankResponse
import com.d208.data.remote.model.MyBeggerRank
import com.d208.data.remote.model.MyGameRank
import com.d208.data.repository.remote.datasource.RankDataSource
import com.d208.data.utils.base.BaseDataSource
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class RankDataSourceImpl @Inject constructor(
    private val rankApi: RankApi,
) : BaseDataSource(), RankDataSource{
    override suspend fun getBeggerRank(remoteErrorEmitter: RemoteErrorEmitter): List<BeggerRankResponse>? {
        return safeApiCall(remoteErrorEmitter){
            rankApi.getBeggerRankTOPTEN()?.body()
        }
    }

    override suspend fun getMyBeggerRank(
        remoteErrorEmitter: RemoteErrorEmitter,
        userId: UUID
    ): MyBeggerRank? {
       return safeApiCall(remoteErrorEmitter){
           rankApi.getMyBeggerRank(userId)?.body()
       }
    }

    override suspend fun getGameRank(remoteErrorEmitter: RemoteErrorEmitter): List<GameRankResponse>? {
        return safeApiCall(remoteErrorEmitter){
            rankApi.getGameRankTOPTEN()?.body()
        }
    }

    override suspend fun getMyGameRank(
        remoteErrorEmitter: RemoteErrorEmitter,
        userId: UUID
    ): MyGameRank? {
        return safeApiCall(remoteErrorEmitter){
            rankApi.getMyGameRank(userId)?.body()
        }
    }

}