package com.sunil.kumar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.status.CommentDataResult
import com.sunil.kumar.domain.status.UserDataResult
import com.sunil.kumar.domain.usecase.GetCommentsUseCase
import com.sunil.kumar.domain.usecase.GetUserDetailUseCase
import com.sunil.kumar.ui.UiState
import com.sunil.kumar.ui.postdetail.PostDetailActivityViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PostDetailActivityViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var scheduler: Scheduler

    @Mock
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase
    @Mock
    private lateinit var getCommentsUseCase: GetCommentsUseCase

    private lateinit var viewModel: PostDetailActivityViewModel

    private val postData = TestAppData.getPostData()
    @Before
    fun setup() {

        scheduler = Schedulers.trampoline()
        viewModel = PostDetailActivityViewModel(
            postData, getCommentsUseCase, getUserDetailUseCase, scheduler
        )
    }

    @Test
    fun shouldReturnDataLoadedState() {

        val userData = TestAppData.getUserDetail()

        whenever(getUserDetailUseCase.execute(TestAppData.USER_ID)).thenReturn(
            Single.just(UserDataResult.Success(userData))
        )

        val commentList = TestAppData.getCommentList()
        whenever(getCommentsUseCase.execute(TestAppData.POST_ID)).thenReturn(
            Single.just(CommentDataResult.Success(commentList))
        )

        viewModel.setUpViewModel()

        assertEquals(LiveDataTestUtil.getValue(viewModel.uiState), UiState.DataLoaded)

        assertEquals(viewModel.userFullName.get(), userData.name)
        assertEquals(viewModel.userTag.get(), "@${userData.username}")
        assertEquals(viewModel.postMessage.get(), postData.body)

        verify(getUserDetailUseCase).execute(TestAppData.USER_ID)
        verify(getCommentsUseCase).execute(TestAppData.POST_ID)
    }


    @Test
    fun shouldReturnLoadingState() {

        whenever(getUserDetailUseCase.execute(TestAppData.USER_ID)).thenReturn(Single.just(UserDataResult.Error))

        assertEquals(LiveDataTestUtil.getValue(viewModel.uiState), UiState.Loading)
        viewModel.setUpViewModel()

        assertEquals(LiveDataTestUtil.getValue(viewModel.uiState), UiState.Error)

        verify(getUserDetailUseCase).execute(TestAppData.USER_ID)
        verify(getCommentsUseCase, never()).execute(any())
    }

    @Test
    fun shouldReturnErrorStateForEmptyPostData() {

        val invalidPostData = PostData("", "", "", "")
        viewModel = PostDetailActivityViewModel(
            invalidPostData, getCommentsUseCase, getUserDetailUseCase, scheduler
        )

        viewModel.setUpViewModel()

        assertEquals(LiveDataTestUtil.getValue(viewModel.uiState), UiState.Error)

        verify(getUserDetailUseCase, never()).execute(any())
        verify(getCommentsUseCase, never()).execute(any())
    }
}
