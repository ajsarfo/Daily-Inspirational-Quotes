package com.cardnotes.inspirationalquotes.data.database.dao.love

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardnotes.inspirationalquotes.data.database.entity.Love

@Dao
interface LoveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Love>)

    @Query("select * from love where special = 1")
    suspend fun special() : List<Love>

    @Query("select * from love where special = 0")
    suspend fun loves() : List<Love>
}