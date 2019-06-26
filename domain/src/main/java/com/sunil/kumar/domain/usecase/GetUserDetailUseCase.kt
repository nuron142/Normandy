package com.sunil.kumar.domain.usecase

import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.status.UserDataResult
import io.reactivex.Scheduler
import io.reactivex.Single

class GetUserDetailUseCase
constructor(
    private val postRepository: PostRepository,
    private val backgroundScheduler: Scheduler
) : UseCaseWithParam<String, UserDataResult> {
    override fun execute(userId: String): Single<UserDataResult> {

        if (userId.isEmpty()) {
            return Single.just(UserDataResult.Error)
        }

        return postRepository.getUserDetail(userId)
            .subscribeOn(backgroundScheduler)
            .map<UserDataResult> { UserDataResult.Success(it) }
            .onErrorReturn { UserDataResult.Error }
            .subscribeOn(backgroundScheduler)

    }

}