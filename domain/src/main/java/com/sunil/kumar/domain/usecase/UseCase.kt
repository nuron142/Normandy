package com.sunil.kumar.domain.usecase

import io.reactivex.Single

interface UseCase<R> {

    fun execute(): Single<R>
}