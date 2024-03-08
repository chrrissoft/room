package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.CategoriesAndSales

@Dao
interface CategoriesAndSalesDao {
    @Insert
    fun insert(data: List<CategoriesAndSales>)

    @Delete
    fun delete(data: List<CategoriesAndSales>)
}
