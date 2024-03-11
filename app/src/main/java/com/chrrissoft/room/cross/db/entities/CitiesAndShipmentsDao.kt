package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments

@Dao
interface CitiesAndShipmentsDao {
    @Insert
    suspend fun insert(data: List<CitiesAndShipments>)

    @Delete
    suspend fun delete(data: List<CitiesAndShipments>)
}
