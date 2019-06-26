package com.sunil.kumar.data

import com.sunil.kumar.data.cache.model.comment.CachedComment
import com.sunil.kumar.domain.models.CommentData
import com.sunil.kumar.domain.models.UserData

object TestDomainData {


    fun getComment(): CommentData {
        return CommentData("1", "1", "Name 1", "Email 1")
    }

    fun getCachedComment(): CachedComment {
        return CachedComment("1", "1", "Name 1", "Email 1")
    }

    fun getUserDetail(): UserData {

        return UserData("1", "Name 1", "UserName 1", "Email 1")
    }
}