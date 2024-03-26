package com.d208.domain.repository

import com.d208.domain.model.DomainComment
import com.d208.domain.model.DomainPost
import com.d208.domain.model.DomainPostDetail
import com.d208.domain.utils.RemoteErrorEmitter
import okhttp3.MultipartBody
import java.util.UUID

interface PostRepository {

    suspend fun registerPost(remoteErrorEmitter: RemoteErrorEmitter, id : UUID, title : String, content : String, postType : String, category : String, file : MultipartBody.Part?) : Long?

    suspend fun getPosts(remoteErrorEmitter: RemoteErrorEmitter, id : UUID) : MutableList<DomainPost>?

    suspend fun pushLike(remoteErrorEmitter: RemoteErrorEmitter, id : Long, userId : UUID) : Unit?

    suspend fun getOnePost(remoteErrorEmitter: RemoteErrorEmitter, id : Long, userId : UUID) : DomainPostDetail?

    suspend fun updatePost(remoteErrorEmitter: RemoteErrorEmitter, id : Long, picture : String, title : String, content : String, postType : String, category : String, file : MultipartBody.Part?) : Long?

    suspend fun getComments(remoteErrorEmitter: RemoteErrorEmitter, id : Long) : MutableList<DomainComment>?

    suspend fun registerComment(remoteErrorEmitter: RemoteErrorEmitter, id : Long, userId : UUID, content : String) : Long?

    suspend fun deleteComment(remoteErrorEmitter: RemoteErrorEmitter, postId : Long, commentId : Long) : Unit?

    suspend fun getPostsByPostType(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID, postType : String) : MutableList<DomainPost>?

    suspend fun deletePost(remoteErrorEmitter: RemoteErrorEmitter, id : Long) : Unit?
}