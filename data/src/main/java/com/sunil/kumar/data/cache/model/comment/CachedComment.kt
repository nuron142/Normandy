package com.sunil.kumar.data.cache.model.comment

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunil.kumar.data.cache.CacheConstants

@Entity(tableName = CacheConstants.COMMENT_TABLE)
data class CachedComment(

    @PrimaryKey
    val id: String,
    val postId: String? = null,
    val name: String? = null,
    val email: String? = null,
    val body: String? = null
)