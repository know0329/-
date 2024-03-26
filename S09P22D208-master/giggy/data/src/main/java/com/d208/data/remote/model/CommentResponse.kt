package com.d208.data.remote.model

import java.util.UUID

data class CommentResponse(val id : Long, val userId : UUID, val nickName : String, val content : String, val createdAt : Long)
