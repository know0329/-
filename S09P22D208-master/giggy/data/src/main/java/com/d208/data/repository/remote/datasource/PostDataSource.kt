package com.d208.data.repository.remote.datasource

import com.d208.data.remote.model.CommentResponse
import com.d208.data.remote.model.PostResponse
import com.d208.domain.model.DomainPost
import com.d208.domain.utils.RemoteErrorEmitter
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import java.util.UUID

interface PostDataSource {

    suspend fun registerPost(remoteErrorEmitter: RemoteErrorEmitter,
                             id : UUID,
                             title : String,
                             content : String,
                             postType : String,
                             category : String,
                             file : MultipartBody.Part?
                             ) : Long?

    suspend fun getPosts(remoteErrorEmitter: RemoteErrorEmitter, id : UUID) : List<PostResponse>?

    suspend fun pushLike(remoteErrorEmitter: RemoteErrorEmitter, id : Long, userId : UUID) : Unit?

    suspend fun getOnePost(remoteErrorEmitter: RemoteErrorEmitter, id : Long, userId : UUID) : PostResponse?
    suspend fun updatePost(remoteErrorEmitter: RemoteErrorEmitter,
                             id : Long,
                             picture : String,
                             title : String,
                             content : String,
                             postType : String,
                             category : String,
                             file : MultipartBody.Part?
    ) : Long?

    suspend fun getComments(remoteErrorEmitter: RemoteErrorEmitter,
    id : Long) : List<CommentResponse> ?

    suspend fun registerComment(remoteErrorEmitter: RemoteErrorEmitter, id : Long, userId : UUID, content : String) : Long?

    suspend fun deleteComment(remoteErrorEmitter: RemoteErrorEmitter, postId : Long, commentId : Long) : Unit?

    suspend fun getPostsByPostType(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID, postType : String) : List<PostResponse>?

    suspend fun deletePost(remoteErrorEmitter: RemoteErrorEmitter, id : Long) : Unit?
}