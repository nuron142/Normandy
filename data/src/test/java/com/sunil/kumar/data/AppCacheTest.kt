package com.sunil.kumar.data

import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunil.kumar.data.cache.AppCache
import com.sunil.kumar.data.cache.AppCacheImpl
import com.sunil.kumar.data.cache.AppDataBase
import com.sunil.kumar.data.cache.model.comment.CommentDataMapper
import com.sunil.kumar.data.cache.model.post.PostDataMapper
import com.sunil.kumar.data.cache.model.user.UserDataMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppCacheTest {

    private lateinit var appDataBase: AppDataBase
    private lateinit var appCache: AppCache
    private lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setup() {

        appDataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ApplicationProvider.getApplicationContext())

        appCache = AppCacheImpl(appDataBase, sharedPreferences, PostDataMapper(), CommentDataMapper(), UserDataMapper())
    }

    @Test
    fun saveUserAndGetUserFromCache() {

        val saveUser = TestDomainData.getUserDetail()
        appCache.saveUser(saveUser)

        val testSubscriber = appCache.getUserDetail(saveUser.id).test()

        testSubscriber.assertValue(saveUser)
        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }

    @Test
    fun shouldReturnInValidUser() {

        val testSubscriber = appCache.getUserDetail("").test()

        val user = testSubscriber.values()[0]
        Assert.assertEquals(user.isInvalid(), true)

        testSubscriber.assertNoErrors()
        testSubscriber.assertComplete()

        testSubscriber.dispose()
    }
}