package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments

@Dao
interface CostumersAndShipmentsDao {
    @Insert
    suspend fun insert(data: List<CostumersAndShipments>)

    @Delete
    suspend fun delete(data: List<CostumersAndShipments>)
}
