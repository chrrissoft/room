package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.ProductsAndSuppliers

@Dao
interface ProductsAndSuppliersDao {
    @Insert
    fun insert(data: List<ProductsAndSuppliers>)

    @Delete
    fun delete(data: List<ProductsAndSuppliers>)
}
