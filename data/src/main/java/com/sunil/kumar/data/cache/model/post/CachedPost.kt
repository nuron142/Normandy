package com.sunil.kumar.data.cache.model.post

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunil.kumar.data.cache.CacheConstants

@Entity(tableName = CacheConstants.POST_TABLE)
data class CachedPost(

    @PrimaryKey
    val id: String,
    val userId: String? = null,
    val title: String? = null,
    val body: String? = null
)