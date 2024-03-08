package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.ProductsAndSales

@Dao
interface ProductsAndSalesDao {
    @Insert
    fun insert(data: List<ProductsAndSales>)

    @Delete
    fun delete(data: List<ProductsAndSales>)
}
