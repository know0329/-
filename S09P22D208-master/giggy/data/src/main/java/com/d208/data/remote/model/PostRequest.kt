package com.d208.data.remote.model

import java.util.UUID

data class PostRequest(
    val category: String,
    val content: String,
    val postType: String,
    val title: String,
    val userId: UUID
)