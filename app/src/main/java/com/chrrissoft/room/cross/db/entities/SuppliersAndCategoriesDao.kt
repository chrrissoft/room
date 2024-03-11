package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.SuppliersAndCategories

@Dao
interface SuppliersAndCategoriesDao {
    @Insert
    suspend fun insert(data: List<SuppliersAndCategories>)

    @Delete
    suspend fun delete(data: List<SuppliersAndCategories>)
}
