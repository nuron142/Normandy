package com.sunil.kumar.ui.postlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sunil.kumar.di.AppModule
import com.sunil.kumar.di.MyViewModelFactory
import com.sunil.kumar.di.ViewModelKey
import com.sunil.kumar.domain.repository.PostRepository
import com.sunil.kumar.domain.usecase.GetPostsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.reactivex.Scheduler
import javax.inject.Named

@Module
abstract class PostListActivityModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesGetPostUseCase(
            postRepository: PostRepository,
            @Named(AppModule.BACKGROUND_SCHEDULER) scheduler: Scheduler
        ): GetPostsUseCase {
            return GetPostsUseCase(postRepository, scheduler)
        }
    }

    @Binds
    abstract fun bindViewModelFactory(factory: MyViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(PostListActivityViewModel::class)
    abstract fun mainViewModel(mainViewModel: PostListActivityViewModel): ViewModel
}