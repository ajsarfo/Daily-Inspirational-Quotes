package com.cardnotes.inspirationalquotes.data.database.dao.author

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardnotes.inspirationalquotes.data.database.entity.Author

@Dao
interface AuthorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(author: Author)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(authors: List<Author>)

    @Query("select * from author order by random() limit :limit")
    suspend fun random(limit: Int) : List<Author>

    @Query("select * from author")
    suspend fun authors() : List<Author>

}