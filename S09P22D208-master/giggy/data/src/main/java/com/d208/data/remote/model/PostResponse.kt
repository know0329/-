package com.d208.data.remote.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class PostResponse(
    val category: String,
    val commentCnt: Int,
    val content: String,
    val id: Long,
    val likeCnt: Int,
    val postType: String,
    val title: String,
    val viewCount: Int,
    val userId : UUID,
    val nickName : String,
    val createdAt : Long,
    @SerializedName("liked")val isLiked : Boolean,
    @SerializedName("picture") val postPicture : String?,
)