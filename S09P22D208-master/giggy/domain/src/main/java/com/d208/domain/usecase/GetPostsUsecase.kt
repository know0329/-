package com.d208.domain.usecase

import com.d208.domain.repository.PostRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class GetPostsUsecase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, id : UUID) = postRepository.getPosts(remoteErrorEmitter, id)

    suspend fun executeFilter(remoteErrorEmitter: RemoteErrorEmitter, userId : UUID, postType : String) = postRepository.getPostsByPostType(remoteErrorEmitter, userId, postType)
}