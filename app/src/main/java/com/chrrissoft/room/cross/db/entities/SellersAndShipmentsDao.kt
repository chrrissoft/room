package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.SellersAndShipments

@Dao
interface SellersAndShipmentsDao {
    @Insert
    suspend fun insert(data: List<SellersAndShipments>)

    @Delete
    suspend fun delete(data: List<SellersAndShipments>)
}
