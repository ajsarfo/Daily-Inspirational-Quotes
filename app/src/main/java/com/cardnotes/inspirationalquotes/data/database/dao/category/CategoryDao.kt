package com.cardnotes.inspirationalquotes.data.database.dao.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cardnotes.inspirationalquotes.data.database.entity.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryList(categories: List<Category>)

    @Query("select * from category order by random() limit :limit")
    suspend fun random(limit: Int) : List<Category>

    @Query("select * from category")
    suspend fun categories() : List<Category>
}