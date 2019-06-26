package com.sunil.kumar.ui.postdetail

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunil.kumar.R
import com.sunil.kumar.di.AppModule
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.status.CommentDataResult
import com.sunil.kumar.domain.status.UserDataResult
import com.sunil.kumar.domain.usecase.GetCommentsUseCase
import com.sunil.kumar.domain.usecase.GetUserDetailUseCase
import com.sunil.kumar.ui.UiState
import com.sunil.kumar.ui.adapter.RecyclerViewModel
import com.sunil.kumar.ui.postdetail.viewmodels.CommentItemViewModel
import com.sunil.kumar.util.Utilities
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Named

open class PostDetailActivityViewModel
@Inject
constructor(
    private val postData: PostData?,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getUserDetailUseCase: GetUserDetailUseCase,
    @Named(AppModule.MAIN_THREAD_SCHEDULER) private val mainThreadScheduler: Scheduler
) : ViewModel() {

    val uiState = MutableLiveData<UiState>(UiState.Loading)

    var dataSet = ObservableArrayList<RecyclerViewModel>()
    val viewModelLayoutIdMap: HashMap<Class<out RecyclerViewModel>, Int> = hashMapOf(
        CommentItemViewModel::class.java to R.layout.item_comment_layout
    )

    private var allDisposables = CompositeDisposable()

    val showProgress = ObservableBoolean(true)

    val userFullName = ObservableField<String>("")
    val userTag = ObservableField<String>("")
    val profileImageUrl = ObservableField<String>("")
    val postMessage = ObservableField<String>("")

    val closeDetailSubject = PublishProcessor.create<Boolean>()

    fun setUpViewModel() {

        val postId = postData?.id
        val userId = postData?.userId

        if (postId != null && userId != null && postId.isNotEmpty() && userId.isNotEmpty()) {
            getUserDetail(postId, userId)
        } else {
            uiState.value = UiState.Error
        }
    }

    private fun getUserDetail(postId: String, userId: String) {

        allDisposables.add(
            getUserDetailUseCase.execute(userId)
                .observeOn(mainThreadScheduler)
                .subscribe { result -> handleUserDetailResponse(postId, result) }
        )
    }

    private fun handleUserDetailResponse(postId: String, result: UserDataResult) {

        when (result) {

            is UserDataResult.Success -> {

                result.userData.apply {
                    userFullName.set(name)
                    userTag.set("@$username")
                    profileImageUrl.set(Utilities.getRandomProfilePicUrl())
                }

                postMessage.set(postData?.body)

                getPostComments(postId)
            }

            UserDataResult.Error -> uiState.value = UiState.Error
        }

    }

    private fun getPostComments(postId: String) {

        allDisposables.add(
            getCommentsUseCase.execute(postId)
                .observeOn(mainThreadScheduler)
                .subscribe { result -> handleCommentsResponse(result) }
        )
    }

    private fun handleCommentsResponse(result: CommentDataResult) {

        when (result) {

            is CommentDataResult.Success -> {

                uiState.value = UiState.DataLoaded
                result.commentDataList.forEach { commentData ->
                    val commentItemViewModel = CommentItemViewModel(commentData)
                    dataSet.add(commentItemViewModel)
                }
            }

            CommentDataResult.Empty -> uiState.value = UiState.Empty

            CommentDataResult.Error -> uiState.value = UiState.Error
        }

    }

    fun onCloseButtonClick(): () -> Unit {

        return {
            closeDetailSubject.offer(true)
        }
    }

    override fun onCleared() {
        super.onCleared()

        allDisposables.dispose()
    }
}
