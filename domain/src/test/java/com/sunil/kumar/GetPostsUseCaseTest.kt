package com.sunil.kumar

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.status.PostDataResult
import com.sunil.kumar.domain.usecase.GetPostsUseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPostsUseCaseTest {

    @Mock
    private lateinit var repository: PostRepository
    private lateinit var scheduler: Scheduler
    private lateinit var getPostsUseCase: GetPostsUseCase

    @Before
    fun setup() {

        scheduler = Schedulers.trampoline()
        getPostsUseCase = GetPostsUseCase(repository, scheduler)
    }

    @Test
    fun shouldReturnListOfPosts() {

        val feeds = TestData.getPostList()

        whenever(repository.getPostList()).thenReturn(Single.just(feeds))

        val testSubscriber = getPostsUseCase.execute().test()

        verify(repository).getPostList()

        testSubscriber.assertValue(PostDataResult.Success(feeds))
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnEmptyListOfPosts() {

        val feeds = emptyList<PostData>()

        whenever(repository.getPostList()).thenReturn(Single.just(feeds))

        val testSubscriber = getPostsUseCase.execute().test()

        verify(repository).getPostList()

        testSubscriber.assertValue(PostDataResult.Empty)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnError() {

        whenever(repository.getPostList()).thenReturn(Single.error(Throwable()))

        val testSubscriber = getPostsUseCase.execute().test()

        verify(repository).getPostList()

        testSubscriber.assertValue(PostDataResult.Error)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

}
