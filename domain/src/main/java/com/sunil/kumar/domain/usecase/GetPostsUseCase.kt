package com.sunil.kumar.domain.usecase

import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.status.PostDataResult
import io.reactivex.Scheduler
import io.reactivex.Single

class GetPostsUseCase
constructor(
    private val postRepository: PostRepository,
    private val backgroundScheduler: Scheduler
) : UseCase<PostDataResult> {
    override fun execute(): Single<PostDataResult> {

        return postRepository.getPostList()
            .map<PostDataResult> {
                if (it.isNotEmpty()) {
                    PostDataResult.Success(it)
                } else {
                    PostDataResult.Empty
                }
            }
            .onErrorReturn { PostDataResult.Error }
            .subscribeOn(backgroundScheduler)
    }

}