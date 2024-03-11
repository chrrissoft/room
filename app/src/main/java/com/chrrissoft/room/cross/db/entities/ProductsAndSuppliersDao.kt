package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.ProductsAndSuppliers

@Dao
interface ProductsAndSuppliersDao {
    @Insert
    suspend fun insert(data: List<ProductsAndSuppliers>)

    @Delete
    suspend fun delete(data: List<ProductsAndSuppliers>)
}
