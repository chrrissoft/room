package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.SellersAndShipments

@Dao
interface SellersAndShipmentsDao {
    @Insert
    fun insert(data: List<SellersAndShipments>)

    @Delete
    fun delete(data: List<SellersAndShipments>)
}
