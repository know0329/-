package com.d208.domain.model

data class SignUpUser(var nickname : String, var accessToken : String, var refreshToken : String, var accountNumber : String, var targetAmount : Int, var fcmToken : String)
