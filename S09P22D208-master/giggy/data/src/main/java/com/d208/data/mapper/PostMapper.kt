package com.d208.data.mapper

import android.util.Log
import com.d208.data.remote.model.CommentResponse
import com.d208.data.remote.model.PostResponse
import com.d208.domain.model.DomainComment
import com.d208.domain.model.DomainPost
import com.d208.domain.model.DomainPostDetail

object PostMapper {

    fun postsMapper(
        response : List<PostResponse>?
    ) : MutableList<DomainPost> ? {
        return if(response != null){
            var list = mutableListOf<DomainPost>()
            for(data in response) {
                list.add(DomainPost(
                    id = data.id,
                    nickName = data.nickName,
                    createdAt = data.createdAt,
                    postType = data.postType,
                    title = data.title,
                    category = data.category,
                    viewCount = data.viewCount,
                    likePost = data.isLiked,
                    likeCount = data.likeCnt,
                ))
            }
            list

        } else null
    }

    fun onePostMapper(
        response: PostResponse?
    ) : DomainPostDetail ? {
        return if(response != null){
            DomainPostDetail(
                id = response.id,
                userId = response.userId,
                category = response.category,
                commentCnt = response.commentCnt,
                content = response.content,
                likeCnt = response.likeCnt,
                postType = response.postType,
                title = response.title,
                viewCount = response.viewCount,
                nickName = response.nickName,
                createAt = response.createdAt,
                isLiked = response.isLiked,
                postPicture = response.postPicture
            )
        } else null
    }

    fun commentMapper(
        response : List<CommentResponse>?
    ) : MutableList<DomainComment> ? {
        return if(response != null){
            var list = mutableListOf<DomainComment>()
            for(data in response) {
                list.add(DomainComment(
                    id = data.id,
                    userId = data.userId,
                    nickName = data.nickName,
                    createdAt = data.createdAt,
                    content = data.content,
                ))
            }
            list
        } else null
    }

}