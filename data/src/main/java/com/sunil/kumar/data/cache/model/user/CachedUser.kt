package com.sunil.kumar.data.cache.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunil.kumar.data.cache.CacheConstants

@Entity(tableName = CacheConstants.USER_TABLE)
data class CachedUser(

    @PrimaryKey
    val id: String,
    val name: String? = null,
    val username: String? = null,
    val email: String? = null
)