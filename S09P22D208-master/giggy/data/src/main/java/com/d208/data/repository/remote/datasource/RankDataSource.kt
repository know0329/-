package com.d208.data.repository.remote.datasource

import com.d208.data.remote.model.BeggerRankResponse
import com.d208.data.remote.model.GameRankResponse
import com.d208.data.remote.model.MyBeggerRank
import com.d208.data.remote.model.MyGameRank
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID

interface RankDataSource {

    suspend fun getBeggerRank(remoteErrorEmitter: RemoteErrorEmitter) : List<BeggerRankResponse>?
    suspend fun getMyBeggerRank(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID) : MyBeggerRank ?
    suspend fun getGameRank(remoteErrorEmitter: RemoteErrorEmitter) : List<GameRankResponse>?
    suspend fun getMyGameRank(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID) : MyGameRank?
}