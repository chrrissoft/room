package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.CitiesAndShipments

@Dao
interface CitiesAndShipmentsDao {
    @Insert
    fun insert(data: List<CitiesAndShipments>)

    @Delete
    fun delete(data: List<CitiesAndShipments>)
}
