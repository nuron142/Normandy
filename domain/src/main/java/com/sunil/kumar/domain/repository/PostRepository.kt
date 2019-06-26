package com.sunil.kumar.domain.repository

import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.models.UserData
import io.reactivex.Single

interface PostRepository {

    fun getPostList(): Single<List<PostData>>

    fun getCommentList(postId: String): Single<List<CommentData>>

    fun getUserDetail(userId: String): Single<UserData>
}