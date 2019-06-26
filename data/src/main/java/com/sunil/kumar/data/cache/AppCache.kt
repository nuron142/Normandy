package com.sunil.kumar.data.cache

import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.models.UserData
import io.reactivex.Single

interface AppCache {

    fun getCachedPostList(): Single<List<PostData>>

    fun saveUser(userData: UserData)

    fun getUserDetail(userId: String): Single<UserData>

    fun saveComments(commentList: List<CommentData>)

    fun getCachedCommentList(postId: String): Single<List<CommentData>>

    fun savePosts(cachedPostList: List<PostData>)
}