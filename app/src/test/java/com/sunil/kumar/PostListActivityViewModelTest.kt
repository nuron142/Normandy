package com.sunil.kumar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import com.sunil.kumar.domain.status.PostDataResult
import com.sunil.kumar.domain.usecase.GetPostsUseCase
import com.sunil.kumar.ui.UiState
import com.sunil.kumar.ui.postlist.PostListActivityViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostListActivityViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var scheduler: Scheduler

    @Mock
    private lateinit var getPostsUseCase: GetPostsUseCase
    private lateinit var postListActivityViewModel: PostListActivityViewModel

    @Before
    fun setup() {

        scheduler = Schedulers.trampoline()
        postListActivityViewModel = PostListActivityViewModel(getPostsUseCase, scheduler)
    }

    @Test
    fun shouldReturnDataLoadedState() {

        val postList = TestAppData.getPostList()

        whenever(getPostsUseCase.execute()).thenReturn(Single.just(PostDataResult.Success(postList)))

        postListActivityViewModel.setUpViewModel()

        assertEquals(LiveDataTestUtil.getValue(postListActivityViewModel.uiState), UiState.DataLoaded)
    }


    @Test
    fun shouldReturnLoadingState() {

        whenever(getPostsUseCase.execute()).thenReturn(Single.just(PostDataResult.Error))

        assertEquals(LiveDataTestUtil.getValue(postListActivityViewModel.uiState), UiState.Loading)
        postListActivityViewModel.setUpViewModel()

        assertEquals(LiveDataTestUtil.getValue(postListActivityViewModel.uiState), UiState.Error)
    }

    @Test
    fun shouldReturnErrorState() {

        whenever(getPostsUseCase.execute()).thenReturn(Single.just(PostDataResult.Error))

        postListActivityViewModel.setUpViewModel()

        assertEquals(LiveDataTestUtil.getValue(postListActivityViewModel.uiState), UiState.Error)
    }

    @Test
    fun shouldReturnEmptyState() {

        whenever(getPostsUseCase.execute()).thenReturn(Single.just(PostDataResult.Empty))

        postListActivityViewModel.setUpViewModel()

        assertEquals(LiveDataTestUtil.getValue(postListActivityViewModel.uiState), UiState.Empty)
    }
}
