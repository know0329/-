package com.d208.data.repository.remote.datasourceimpl

import com.d208.data.remote.api.PostApi
import com.d208.data.remote.model.CommentRegisterRequest
import com.d208.data.remote.model.CommentResponse
import com.d208.data.remote.model.PostRequest
import com.d208.data.remote.model.PostResponse
import com.d208.data.remote.model.PostUpdateRequest
import com.d208.data.repository.remote.datasource.PostDataSource
import com.d208.data.utils.base.BaseDataSource
import com.d208.domain.model.DomainPost
import com.d208.domain.utils.RemoteErrorEmitter
import okhttp3.MultipartBody
import java.util.UUID
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val postApi: PostApi
) : BaseDataSource(), PostDataSource{
    override suspend fun registerPost(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID,
        title: String,
        content: String,
        postType: String,
        category: String,
        file : MultipartBody.Part?
    ): Long? {
        return safeApiCall(remoteErrorEmitter){
            val data =  PostRequest(userId = id, title = title, content = content, postType = postType, category = category)
            postApi.registerPost(data, file).body()
        }
    }

    override suspend fun getPosts(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: UUID
    ): List<PostResponse>? {
        return safeApiCall(remoteErrorEmitter){
            postApi.getPosts(id).body()
        }
    }

    override suspend fun pushLike(remoteErrorEmitter: RemoteErrorEmitter, id: Long, userId: UUID) : Unit? {
        return safeApiCall(remoteErrorEmitter){
            postApi.pushLike(id, userId)
        }
    }

    override suspend fun getOnePost(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: Long,
        userId: UUID
    ): PostResponse? {
        return safeApiCall(remoteErrorEmitter){
            postApi.getOnePost(id, userId).body()
        }
    }

    override suspend fun updatePost(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: Long,
        picture : String,
        title: String,
        content: String,
        postType: String,
        category: String,
        file: MultipartBody.Part?
    ): Long? {
        return safeApiCall(remoteErrorEmitter){
            val data =  PostUpdateRequest(title = title, content = content, postType = postType, category = category, picture = picture)
            postApi.updatePost(id, data, file).body()
        }
    }

    override suspend fun getComments(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: Long
    ): List<CommentResponse>? {
       return safeApiCall(remoteErrorEmitter){
           postApi.getComments(id).body()
       }
    }

    override suspend fun registerComment(
        remoteErrorEmitter: RemoteErrorEmitter,
        id: Long,
        userId: UUID,
        content: String
    ): Long? {
        return safeApiCall(remoteErrorEmitter){
            val data = CommentRegisterRequest(userId = userId, content = content)
            postApi.registerComment(id, data).body()
        }
    }

    override suspend fun deleteComment(
        remoteErrorEmitter: RemoteErrorEmitter,
        postId: Long,
        commentId: Long
    ): Unit? {
        return safeApiCall(remoteErrorEmitter){
            postApi.deleteComment(postId, commentId)
        }
    }

    override suspend fun getPostsByPostType(
        remoteErrorEmitter: RemoteErrorEmitter,
        userId: UUID,
        postType: String
    ): List<PostResponse>? {
        return safeApiCall(remoteErrorEmitter){
            postApi.getPostsByPostType(userId, postType).body()
        }
    }

    override suspend fun deletePost(remoteErrorEmitter: RemoteErrorEmitter, id: Long): Unit? {
        return safeApiCall(remoteErrorEmitter){
            postApi.deletePost(id)
        }
    }
}