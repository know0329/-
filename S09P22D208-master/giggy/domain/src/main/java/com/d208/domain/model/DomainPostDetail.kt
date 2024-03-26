package com.d208.domain.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class DomainPostDetail(
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
    val createAt : Long,
    val isLiked : Boolean,
    val postPicture : String?
)
