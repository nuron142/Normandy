package com.sunil.kumar.data

import com.sunil.kumar.data.cache.AppCache
import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.models.UserData
import com.sunil.kumar.domain.repository.PostRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepositoryImpl
@Inject
constructor(
    private val apiService: ApiService,
    private val appCache: AppCache
) : PostRepository {

    override fun getPostList(): Single<List<PostData>> {

        val apiSingle = apiService.getPostList().map { list ->
            appCache.savePosts(list)
            return@map list
        }

        return appCache.getCachedPostList()
            .flatMap { list ->
                if (list.isNotEmpty()) {
                    Single.just(list)
                } else {
                    apiSingle
                }
            }
    }

    override fun getCommentList(postId: String): Single<List<CommentData>> {

        val apiSingle = apiService.getCommentList(postId).map { list ->
            appCache.saveComments(list)
            return@map list
        }

        return appCache.getCachedCommentList(postId)
            .flatMap { list ->
                if (list.isNotEmpty()) {
                    Single.just(list)
                } else {
                    apiSingle
                }
            }
    }

    override fun getUserDetail(userId: String): Single<UserData> {

        val apiSingle = apiService.getUserDetail(userId).map { user ->
            appCache.saveUser(user)
            return@map user
        }

        return appCache.getUserDetail(userId)
            .flatMap { user ->
                if (!user.isInvalid()) {
                    Single.just(user)
                } else {
                    apiSingle
                }
            }
    }

}