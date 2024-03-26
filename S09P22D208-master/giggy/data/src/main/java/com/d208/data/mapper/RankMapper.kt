package com.d208.data.mapper

import com.d208.data.remote.model.BeggerRankResponse
import com.d208.data.remote.model.GameRankResponse
import com.d208.data.remote.model.MyBeggerRank
import com.d208.data.remote.model.MyGameRank
import com.d208.domain.model.DomainBeggerRank
import com.d208.domain.model.DomainGameRank
import com.d208.domain.model.DomainMyBegger
import com.d208.domain.model.DomainMyGame

object RankMapper {

    fun getBeggerRank(
        response : List<BeggerRankResponse>?
    ) : List<DomainBeggerRank> ? {
        return if(response != null){
            val list =  mutableListOf<DomainBeggerRank>()
            for(data in response){
                list.add(DomainBeggerRank(
                    nickname = data.nickName,
                    ratio = data.ratio,
                ))
            }
            list
        }
        else response
    }

    fun getGameRank(
        response : List<GameRankResponse>?
    ) : List<DomainGameRank> ? {
        return if(response != null){
            val list = mutableListOf<DomainGameRank>()
            for(data in response){
               list.add(DomainGameRank(
                   rank = data.rank,
                   nickname = data.nickname,
                   score = data.score
               ))
            }
            list
        }
        else response

    }

    fun getMyBeggerRank(
        response : MyBeggerRank?
    ) : DomainMyBegger ? {
        return if(response != null){
            DomainMyBegger(
                nickName = "",
                rank = response.rank,
                ratio = response.ratio,
            )
        }
        else response
    }

    fun getMyGameRank(
        response: MyGameRank?
    ) : DomainMyGame ? {
        return if(response != null){
            DomainMyGame(
                nickname = "",
                score = response.score,
                rank = response.rank,
                leftLife = response.leftLife,
            )
        }
        else response
    }
}