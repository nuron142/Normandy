package com.sunil.kumar.data.cache

import android.content.SharedPreferences
import com.sunil.kumar.data.cache.model.comment.CommentDataMapper
import com.sunil.kumar.data.cache.model.post.PostDataMapper
import com.sunil.kumar.data.cache.model.user.UserDataMapper
import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.models.UserData
import io.reactivex.Single
import javax.inject.Inject

class AppCacheImpl
@Inject
constructor(
    private val appDataBase: AppDataBase,
    private val sharedPreferences: SharedPreferences,
    private val postDataMapper: PostDataMapper,
    private val commentDataMapper: CommentDataMapper,
    private val userDataMapper: UserDataMapper
) : AppCache {

    private val EXPIRATION_TIME = (5 * 60 * 1000).toLong() //5 mins

    private val POSTS_CACHE_KEY = "POSTS_CACHE_KEY"
    private val COMMENTS_CACHE_KEY = "COMMENTS_CACHE_KEY"
    private val USER_CACHE_KEY = "USER_CACHE_KEY"

    override fun getCachedPostList(): Single<List<PostData>> {

        return Single.defer {
            if (isExpired(POSTS_CACHE_KEY)) {
                appDataBase.cachedPostDAO().deleteAll()
                Single.just(emptyList())
            } else {
                Single.just(appDataBase.cachedPostDAO().getAllCachedPost()
                    .map { postDataMapper.mapFromCached(it) })
            }
        }
    }

    override fun savePosts(cachedPostList: List<PostData>) {

        appDataBase.cachedPostDAO().insertAll(cachedPostList.map { postDataMapper.mapToCached(it) })
        setLastCacheTime(POSTS_CACHE_KEY, System.currentTimeMillis())
    }


    override fun getCachedCommentList(postId: String): Single<List<CommentData>> {

        return Single.defer {
            if (isExpired(COMMENTS_CACHE_KEY)) {
                appDataBase.cachedCommentDAO().deleteAll()
                Single.just(emptyList())
            } else {
                Single.just(appDataBase.cachedCommentDAO().getAllCachedPostById(postId)
                    .map { commentDataMapper.mapFromCached(it) })
            }
        }
    }

    override fun saveComments(commentList: List<CommentData>) {

        appDataBase.cachedCommentDAO().insertAll(commentList.map { commentDataMapper.mapToCached(it) })
        setLastCacheTime(COMMENTS_CACHE_KEY, System.currentTimeMillis())
    }

    override fun getUserDetail(userId: String): Single<UserData> {

        return Single.defer {

            val user = appDataBase.cachedUserDAO().getUserById(userId)
            if (user == null || isExpired(USER_CACHE_KEY)) {
                appDataBase.cachedUserDAO().deleteAll()
                Single.just(UserData(""))
            } else {
                Single.just(user).map { userDataMapper.mapFromCached(it) }
            }
        }
    }

    override fun saveUser(userData: UserData) {

        appDataBase.cachedUserDAO().insert(userDataMapper.mapToCached(userData))
        setLastCacheTime(USER_CACHE_KEY, System.currentTimeMillis())
    }

    private fun isExpired(cacheKey: String): Boolean {

        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis(cacheKey)
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheUpdateTimeMillis(cacheKey: String): Long {
        return sharedPreferences.getLong(cacheKey, 0)
    }

    private fun setLastCacheTime(cacheKey: String, lastCache: Long) {
        sharedPreferences.edit().putLong(cacheKey, lastCache).apply()
    }

}