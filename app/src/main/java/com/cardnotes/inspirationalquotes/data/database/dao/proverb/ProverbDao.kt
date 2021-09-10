package com.cardnotes.inspirationalquotes.data.database.dao.proverb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardnotes.inspirationalquotes.data.database.entity.Proverb

@Dao
interface ProverbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<Proverb>)

    @Query("select * from proverb")
    suspend fun proverbs() : List<Proverb>
}