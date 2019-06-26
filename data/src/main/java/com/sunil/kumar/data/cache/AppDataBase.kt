package com.sunil.kumar.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sunil.kumar.data.cache.model.comment.CachedComment
import com.sunil.kumar.data.cache.model.comment.CachedCommentDAO
import com.sunil.kumar.data.cache.model.post.CachedPost
import com.sunil.kumar.data.cache.model.post.CachedPostDAO
import com.sunil.kumar.data.cache.model.user.CachedUser
import com.sunil.kumar.data.cache.model.user.CachedUserDAO

@Database(entities = [CachedPost::class, CachedUser::class, CachedComment::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun cachedPostDAO(): CachedPostDAO

    abstract fun cachedUserDAO(): CachedUserDAO

    abstract fun cachedCommentDAO(): CachedCommentDAO
}