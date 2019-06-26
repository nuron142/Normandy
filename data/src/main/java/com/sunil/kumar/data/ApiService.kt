package com.sunil.kumar.data

import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.models.UserData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    fun getPostList(): Single<List<PostData>>

    @GET("users/{userId}")
    fun getUserDetail(@Path("userId") userId: String): Single<UserData>

    @GET("comments")
    fun getCommentList(@Query("postId") postId: String): Single<List<CommentData>>
}