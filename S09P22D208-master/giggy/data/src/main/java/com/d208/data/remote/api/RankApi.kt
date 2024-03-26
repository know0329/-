package com.d208.data.remote.api

import com.d208.data.remote.model.BeggerRankResponse
import com.d208.data.remote.model.GameRankResponse
import com.d208.data.remote.model.MyBeggerRank
import com.d208.data.remote.model.MyGameRank
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.UUID

interface RankApi {


    @GET("app/hall-of-begger/top")
    suspend fun getBeggerRankTOPTEN() : Response<List<BeggerRankResponse>>

    @GET("app/game/top-ten")
    suspend fun getGameRankTOPTEN() : Response<List<GameRankResponse>>

    @POST("app/hall-of-begger")
    suspend fun getMyBeggerRank(@Body userId : UUID) : Response<MyBeggerRank>

    @GET("app/game/my-status/{userId}")
    suspend fun getMyGameRank(@Path("userId") userId : UUID) : Response<MyGameRank>

}