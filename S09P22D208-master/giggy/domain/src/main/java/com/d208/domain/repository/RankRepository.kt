package com.d208.domain.repository

import com.d208.domain.model.DomainBeggerRank
import com.d208.domain.model.DomainGameRank
import com.d208.domain.model.DomainMyBegger
import com.d208.domain.model.DomainMyGame
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID

interface RankRepository {

    suspend fun getBeggerRank(remoteErrorEmitter: RemoteErrorEmitter) : List<DomainBeggerRank>?

    suspend fun getMyBeggerRank(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID) : DomainMyBegger?

    suspend fun getGameRank(remoteErrorEmitter: RemoteErrorEmitter) : List<DomainGameRank>?

    suspend fun getMyGameRank(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID) : DomainMyGame?
}