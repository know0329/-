package com.d208.data.remote.model

data class LoginData(
    val accessToken: String,
    val fcmToken: String,
    val refreshToken: String
)