package com.d208.domain.usecase

import com.d208.domain.repository.PostRepository
import com.d208.domain.utils.RemoteErrorEmitter
import okhttp3.MultipartBody
import javax.inject.Inject

class PostUpdateUsecase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, id : Long, picture : String, title : String, content : String, postType : String, category : String, file : MultipartBody.Part? ) = postRepository.updatePost(remoteErrorEmitter, id, picture, title, content, postType, category, file)
}