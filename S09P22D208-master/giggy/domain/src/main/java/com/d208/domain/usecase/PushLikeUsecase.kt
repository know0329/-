package com.d208.domain.usecase

import com.d208.domain.repository.PostRepository
import com.d208.domain.utils.RemoteErrorEmitter
import java.util.UUID
import javax.inject.Inject

class PushLikeUsecase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, id : Long, userId : UUID) = postRepository.pushLike(remoteErrorEmitter, id, userId)
}