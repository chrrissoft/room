package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.CostumersAndShipments

@Dao
interface CostumersAndShipmentsDao {
    @Insert
    fun insert(data: List<CostumersAndShipments>)

    @Delete
    fun delete(data: List<CostumersAndShipments>)
}
