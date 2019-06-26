package com.sunil.kumar.data.cache.model.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedPostDAO {

    @Query("SELECT * from POST_TABLE ORDER BY id ASC")
    fun getAllCachedPost(): List<CachedPost>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(word: List<CachedPost>)

    @Query("DELETE FROM POST_TABLE")
    fun deleteAll()
}