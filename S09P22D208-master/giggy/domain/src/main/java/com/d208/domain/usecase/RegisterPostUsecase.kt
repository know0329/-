package com.d208.domain.usecase

import com.d208.domain.repository.PostRepository
import com.d208.domain.utils.RemoteErrorEmitter
import okhttp3.MultipartBody
import java.util.UUID
import javax.inject.Inject

class RegisterPostUsecase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, id : UUID, title : String, content : String, postType : String, category : String, file : MultipartBody.Part?) = postRepository.registerPost(remoteErrorEmitter, id, title, content, postType, category, file)
}