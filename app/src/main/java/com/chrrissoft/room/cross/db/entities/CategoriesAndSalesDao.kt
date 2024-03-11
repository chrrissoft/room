package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.CategoriesAndSales

@Dao
interface CategoriesAndSalesDao {
    @Insert
    suspend fun insert(data: List<CategoriesAndSales>)

    @Delete
    suspend fun delete(data: List<CategoriesAndSales>)
}
