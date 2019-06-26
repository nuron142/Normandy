package com.sunil.kumar.domain.usecase

import io.reactivex.Single

interface UseCaseWithParam<P, R> {

    fun execute(param: P): Single<R>
}