package com.sunil.kumar.data.cache.model.comment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedCommentDAO {

    @Query("SELECT * FROM COMMENT_TABLE WHERE postId = :postId")
    fun getAllCachedPostById(postId:String): List<CachedComment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(word: List<CachedComment>)

    @Query("DELETE FROM COMMENT_TABLE")
    fun deleteAll()
}