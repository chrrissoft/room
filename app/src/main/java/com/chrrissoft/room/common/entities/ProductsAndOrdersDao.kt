package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.ProductsAndOrders

@Dao
interface ProductsAndOrdersDao {
    @Insert
    fun insert(data: List<ProductsAndOrders>)

    @Delete
    fun delete(data: List<ProductsAndOrders>)
}
