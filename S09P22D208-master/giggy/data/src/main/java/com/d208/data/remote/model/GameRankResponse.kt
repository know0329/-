package com.d208.data.remote.model

import java.util.UUID

data class GameRankResponse(val userId : UUID, val score : Int, val rank : Int, val nickname : String, )
