package com.d208.data.remote.model

import java.util.UUID

data class MyGameRank(
    val leftLife: Int,
    val nickname: String,
    val rank: Int,
    val score: Int,
    val userId: UUID
)