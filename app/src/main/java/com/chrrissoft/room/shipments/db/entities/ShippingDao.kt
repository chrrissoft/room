package com.chrrissoft.room.shipments.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface ShippingDao {
    @Query("SELECT * FROM shipments WHERE id = :id")
    fun get(id: String) : Flow<ShippingWithRelationship>

    @Query("SELECT * FROM shipments")
    fun get() : Flow<List<ShippingWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Shipping>)

    @Delete
    suspend fun delete(data: List<Shipping>)
}
