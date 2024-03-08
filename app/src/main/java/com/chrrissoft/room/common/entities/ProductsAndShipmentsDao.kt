package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.ProductsAndShipments

@Dao
interface ProductsAndShipmentsDao {
    @Insert
    fun insert(data: List<ProductsAndShipments>)

    @Delete
    fun delete(data: List<ProductsAndShipments>)
}
