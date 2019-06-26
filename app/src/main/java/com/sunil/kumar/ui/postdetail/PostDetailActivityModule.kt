package com.sunil.kumar.ui.postdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sunil.kumar.di.AppModule
import com.sunil.kumar.di.MyViewModelFactory
import com.sunil.kumar.di.ViewModelKey
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.usecase.GetCommentsUseCase
import com.sunil.kumar.domain.usecase.GetUserDetailUseCase
import com.sunil.kumar.util.toClassData
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.Scheduler
import javax.inject.Named

@Module
abstract class PostDetailActivityModule {
    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesPostData(postDetailActivity: PostDetailActivity): PostData? {
            return postDetailActivity.intent?.getStringExtra(PostDetailActivity.POST_DATA)
                ?.toClassData(PostData::class.java)
        }

        @Provides
        @JvmStatic
        fun providesGetUserDetailUseCase(
            postRepository: PostRepository,
            @Named(AppModule.BACKGROUND_SCHEDULER) scheduler: Scheduler
        ): GetUserDetailUseCase {
            return GetUserDetailUseCase(postRepository, scheduler)
        }

        @Provides
        @JvmStatic
        fun providesGetCommentsUseCase(
            postRepository: PostRepository,
            @Named(AppModule.BACKGROUND_SCHEDULER) scheduler: Scheduler
        ): GetCommentsUseCase {
            return GetCommentsUseCase(postRepository, scheduler)
        }
    }

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PostDetailActivityViewModel::class)
    abstract fun mainViewModel(mainViewModel: PostDetailActivityViewModel): ViewModel
}