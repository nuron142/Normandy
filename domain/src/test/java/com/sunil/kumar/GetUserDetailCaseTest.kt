package com.sunil.kumar

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.status.UserDataResult
import com.sunil.kumar.domain.usecase.GetUserDetailUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetUserDetailCaseTest {

    @Mock
    private lateinit var repository: PostRepository
    private lateinit var scheduler: Scheduler
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setup() {

        scheduler = Schedulers.trampoline()
        getUserDetailUseCase = GetUserDetailUseCase(repository, scheduler)
    }

    @Test
    fun shouldReturnListOfPosts() {

        val userData = TestData.getUserDetail()

        whenever(repository.getUserDetail(TestData.USER_ID)).thenReturn(Single.just(userData))

        val testSubscriber = getUserDetailUseCase.execute(TestData.USER_ID).test()

        verify(repository).getUserDetail(TestData.USER_ID)

        testSubscriber.assertValue(UserDataResult.Success(userData))
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnErrorForEmptyUserId() {

        val testSubscriber = getUserDetailUseCase.execute("").test()

        verify(repository, never()).getUserDetail("")

        testSubscriber.assertValue(UserDataResult.Error)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnError() {

        whenever(repository.getUserDetail(TestData.USER_ID)).thenReturn(Single.error(Throwable()))

        val testSubscriber = getUserDetailUseCase.execute(TestData.USER_ID).test()

        verify(repository).getUserDetail(TestData.USER_ID)

        testSubscriber.assertValue(UserDataResult.Error)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

}
