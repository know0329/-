package com.d208.data.remote.model

import java.time.LocalDateTime
import java.util.UUID

data class LoginUser(
    val email: String = "",
    val fcmToken: String = "",
    val id: UUID? = null,
    val leftLife: Int = 0,
    val nickname: String = "",
    val refreshToken: String = "",
    val targetAmount: Int = 0,
    val birthday : String,
    val currentAmount : Int = 0,
    val registerDate : Long
)