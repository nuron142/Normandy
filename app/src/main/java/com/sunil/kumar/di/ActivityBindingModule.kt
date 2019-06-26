package com.sunil.kumar.di

import com.sunil.kumar.ui.postdetail.PostDetailActivity
import com.sunil.kumar.ui.postdetail.PostDetailActivityModule
import com.sunil.kumar.ui.postlist.PostListActivity
import com.sunil.kumar.ui.postlist.PostListActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module where Activity dependencies are defined
 */

@Module
abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [PostListActivityModule::class])
    abstract fun postListActivity(): PostListActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PostDetailActivityModule::class])
    abstract fun postDetailActivity(): PostDetailActivity
}
