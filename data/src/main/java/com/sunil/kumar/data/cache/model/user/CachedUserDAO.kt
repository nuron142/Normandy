package com.sunil.kumar.data.cache.model.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CachedUserDAO {

    @Query("SELECT * FROM USER_TABLE WHERE id = :userId")
    fun getUserById(userId: String): CachedUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: CachedUser)

    @Query("DELETE FROM USER_TABLE")
    fun deleteAll()
}