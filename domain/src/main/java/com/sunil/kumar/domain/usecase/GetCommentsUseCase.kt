package com.sunil.kumar.domain.usecase

import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.status.CommentDataResult
import io.reactivex.Scheduler
import io.reactivex.Single

class GetCommentsUseCase
constructor(
    private val postRepository: PostRepository,
    private val backgroundScheduler: Scheduler
) : UseCaseWithParam<String, CommentDataResult> {
    override fun execute(postId: String): Single<CommentDataResult> {

        if (postId.isEmpty()) {
            return Single.just(CommentDataResult.Error)
        }

        return postRepository.getCommentList(postId)
            .map<CommentDataResult> {
                if (it.isNotEmpty()) {
                    CommentDataResult.Success(it)
                } else {
                    CommentDataResult.Empty
                }
            }
            .onErrorReturn { CommentDataResult.Error }
            .subscribeOn(backgroundScheduler)
    }

}