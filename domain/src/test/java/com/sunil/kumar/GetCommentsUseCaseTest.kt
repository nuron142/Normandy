package com.sunil.kumar

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.status.CommentDataResult
import com.sunil.kumar.domain.usecase.GetCommentsUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCommentsUseCaseTest {

    @Mock
    private lateinit var repository: PostRepository
    private lateinit var scheduler: Scheduler
    private lateinit var getCommentsUseCase: GetCommentsUseCase

    @Before
    fun setup() {

        scheduler = Schedulers.trampoline()
        getCommentsUseCase = GetCommentsUseCase(repository, scheduler)
    }

    @Test
    fun shouldReturnListOfComments() {

        val feeds = TestData.getCommentList()

        whenever(repository.getCommentList(TestData.POST_ID)).thenReturn(Single.just(feeds))

        val testSubscriber = getCommentsUseCase.execute(TestData.POST_ID).test()

        verify(repository).getCommentList(TestData.POST_ID)

        testSubscriber.assertValue(CommentDataResult.Success(feeds))
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnEmptyListOfComments() {

        val feeds = emptyList<CommentData>()

        whenever(repository.getCommentList(TestData.POST_ID)).thenReturn(Single.just(feeds))

        val testSubscriber = getCommentsUseCase.execute(TestData.POST_ID).test()

        verify(repository).getCommentList(TestData.POST_ID)

        testSubscriber.assertValue(CommentDataResult.Empty)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnError() {

        whenever(repository.getCommentList(TestData.POST_ID)).thenReturn(Single.error(Throwable()))

        val testSubscriber = getCommentsUseCase.execute(TestData.POST_ID).test()

        verify(repository).getCommentList(TestData.POST_ID)

        testSubscriber.assertValue(CommentDataResult.Error)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnErrorForEmptyUserId() {

        val testSubscriber = getCommentsUseCase.execute("").test()

        verify(repository, never()).getCommentList("")

        testSubscriber.assertValue(CommentDataResult.Error)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }
}
