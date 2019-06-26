package com.sunil.kumar

import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.models.PostData
import com.sunil.kumar.domain.models.UserData

object TestData {

    const val POST_ID = "1"

    const val USER_ID = "1"

    fun getPostList(): List<PostData> {

        val list = mutableListOf<PostData>()

        list.add(PostData("1", "1", "Title 1", "Body 1"))
        list.add(PostData("2", "2", "Title 2", "Body 2"))
        list.add(PostData("3", "3", "Title 3", "Body 3"))
        list.add(PostData("4", "4", "Title 4", "Body 4"))

        return list
    }

    fun getCommentList(): List<CommentData> {

        val list = mutableListOf<CommentData>()

        list.add(CommentData("1", "1", "Name 1", "Email 1"))
        list.add(CommentData("2", "2", "Name 2", "Email 2"))
        list.add(CommentData("3", "3", "Name 3", "Email 3"))
        list.add(CommentData("4", "4", "Name 4", "Email 4"))

        return list
    }

    fun getComment(): CommentData {
        return CommentData("1", "1", "Name 1", "Email 1")
    }

    fun getUserDetail(): UserData {

        return UserData("1", "Name 1", "UserName 1", "Email 1")
    }
}