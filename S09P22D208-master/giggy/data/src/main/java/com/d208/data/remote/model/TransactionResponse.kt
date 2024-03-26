package com.d208.data.remote.model

import java.time.LocalDateTime
import java.util.UUID

data class TransactionResponse( val id : Long,
                                val amount : Int,
                                val content : String,
                                val transactionDate : Long,
                                val transactionType : String,
                                val category : String,
                                val deposit : Int,
                                val withdraw :Int,
)

