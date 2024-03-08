package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.SupplierAndSales

@Dao
interface SupplierAndSalesDao {
    @Insert
    fun insert(data: List<SupplierAndSales>)

    @Delete
    fun delete(data: List<SupplierAndSales>)
}
