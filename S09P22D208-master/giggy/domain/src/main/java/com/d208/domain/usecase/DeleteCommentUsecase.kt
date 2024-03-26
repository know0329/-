package com.d208.domain.usecase

import com.d208.domain.repository.PostRepository
import com.d208.domain.utils.RemoteErrorEmitter
import javax.inject.Inject

class DeleteCommentUsecase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun execute(remoteErrorEmitter: RemoteErrorEmitter, postId : Long, commentId : Long) = postRepository.deleteComment(remoteErrorEmitter, postId, commentId)
}