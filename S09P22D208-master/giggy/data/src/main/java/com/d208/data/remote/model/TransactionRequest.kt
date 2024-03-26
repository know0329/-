package com.d208.data.remote.model

import java.util.UUID

data class TransactionRequest(val userId : UUID, val startDate : String , val endDate : String )
