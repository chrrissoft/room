package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.SuppliersAndCategories

@Dao
interface SuppliersAndCategoriesDao {
    @Insert
    fun insert(data: List<SuppliersAndCategories>)

    @Delete
    fun delete(data: List<SuppliersAndCategories>)
}
