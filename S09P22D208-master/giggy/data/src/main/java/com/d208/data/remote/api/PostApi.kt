package com.d208.data.remote.api

import com.d208.data.remote.model.CommentResponse
import com.d208.data.remote.model.CommentRegisterRequest
import com.d208.data.remote.model.PostRequest
import com.d208.data.remote.model.PostResponse
import com.d208.data.remote.model.PostUpdateRequest
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface PostApi {
    @Multipart
    @POST("app/post")
    suspend fun registerPost(@Part("postCreateDto") data : PostRequest, @Part file : MultipartBody.Part?) : Response<Long>

    @POST("app/post/{userId}")
    suspend fun getPosts(@Path("userId") id : UUID) : Response<List<PostResponse>>

    //하나 조회
    @POST("app/post/{postId}/{userId}")
    suspend fun getOnePost(@Path("postId") id : Long, @Path("userId") userId : UUID) : Response<PostResponse>

    @POST("app/post/{postId}/like")
    suspend fun pushLike(@Path("postId") id : Long, @Body userId : UUID)

    @Multipart
    @PUT("app/post/{postId}")
    suspend fun updatePost(@Path("postId") id : Long, @Part("postUpdateDto") data : PostUpdateRequest, @Part file : MultipartBody.Part?) : Response<Long>

    @GET("app/post/{postId}/comment")
    suspend fun getComments(@Path("postId") id : Long) : Response<List<CommentResponse>>


    @POST("app/post/{postId}/comment")
    suspend fun registerComment(@Path("postId") id : Long, @Body data : CommentRegisterRequest) :Response<Long>

    @DELETE("app/post/{postId}/comment/{commentId}")
    suspend fun deleteComment(@Path("postId") id : Long, @Path("commentId") commentId : Long)

    //필터된 리스트 받기
    @POST("app/post/{currentUserId}")
    suspend fun getPostsByPostType(@Path("currentUserId") userId : UUID, @Query("postType") postType : String ) : Response<List<PostResponse>>

    @DELETE("app/post/{postId}")
    suspend fun deletePost(@Path("postId") id : Long) : Unit?
}