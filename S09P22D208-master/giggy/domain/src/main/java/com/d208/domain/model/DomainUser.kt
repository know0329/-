package com.d208.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class DomainUser(var id : UUID? = null, var email : String? = "", var nickname : String? = "", var targetAmount : Int = 0, val currentAmount : Int = 0, var fcmToken : String? ="", var refreshToken : String? = "", var leftLife : Int? =0, var birthday : String? = "", var registerDate : Long? = null)
