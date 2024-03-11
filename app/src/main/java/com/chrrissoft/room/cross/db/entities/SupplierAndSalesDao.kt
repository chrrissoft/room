package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.SuppliersAndSales

@Dao
interface SupplierAndSalesDao {
    @Insert
    suspend fun insert(data: List<SuppliersAndSales>)

    @Delete
    suspend fun delete(data: List<SuppliersAndSales>)
}
