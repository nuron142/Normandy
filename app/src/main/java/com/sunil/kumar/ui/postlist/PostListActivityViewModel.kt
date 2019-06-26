package com.sunil.kumar.ui.postlist

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sunil.kumar.R
import com.sunil.kumar.di.AppModule
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.status.PostDataResult
import com.sunil.kumar.domain.usecase.GetPostsUseCase
import com.sunil.kumar.ui.UiState
import com.sunil.kumar.ui.postlist.viewmodels.PostListItemViewModel
import com.sunil.kumar.ui.adapter.RecyclerViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Named

open class PostListActivityViewModel
@Inject
constructor(
    private val getPostsUseCase: GetPostsUseCase,
    @Named(AppModule.MAIN_THREAD_SCHEDULER) private val mainThreadScheduler: Scheduler
) : ViewModel() {

    val uiState = MutableLiveData<UiState>(UiState.Loading)
    var dataSet = ObservableArrayList<RecyclerViewModel>()
    val viewModelLayoutIdMap: HashMap<Class<out RecyclerViewModel>, Int> = hashMapOf(
        PostListItemViewModel::class.java to R.layout.item_post_list_layout
    )

    val showProgress = ObservableBoolean(false)
    val openDetailPageSubject = PublishProcessor.create<PostData>()

    private val allDisposables = CompositeDisposable()

    fun setUpViewModel() {

        getPosts()
    }

    private fun getPosts() {

        allDisposables.add(
            getPostsUseCase.execute()
                .observeOn(mainThreadScheduler)
                .subscribe { result -> handlePostListResponse(result) }
        )
    }

    private fun handlePostListResponse(result: PostDataResult) {

        when (result) {

            is PostDataResult.Success -> {

                uiState.value = UiState.DataLoaded

                result.postDataList.forEach { postData ->

                    val postListItemViewModel = PostListItemViewModel(postData) { data ->
                        openDetailPageSubject.offer(data)
                    }
                    dataSet.add(postListItemViewModel)
                }
            }

            PostDataResult.Empty -> uiState.value = UiState.Empty

            PostDataResult.Error -> uiState.value = UiState.Error
        }
    }

    override fun onCleared() {
        super.onCleared()

        allDisposables.dispose()
    }
}
