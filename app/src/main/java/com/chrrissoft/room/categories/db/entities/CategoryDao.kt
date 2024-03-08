package com.chrrissoft.room.categories.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithNestedRelationship
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories WHERE id = :id")
    fun get(id: String) : Flow<CategoryWithNestedRelationship>

    @Query("SELECT * FROM categories")
    fun get() : Flow<List<CategoryWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Category>)

    @Delete
    suspend fun delete(data: List<Category>)
}
