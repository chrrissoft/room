package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.chrrissoft.room.cross.db.objects.ProductsAndSales

@Dao
interface ProductsAndSalesDao {
    @Query("SELECT * FROM products_and_sales LIMIT 1")
    suspend fun get() : ProductsAndSales

    @Insert
    suspend fun insert(data: List<ProductsAndSales>)

    @Delete
    suspend fun delete(data: List<ProductsAndSales>)
}
