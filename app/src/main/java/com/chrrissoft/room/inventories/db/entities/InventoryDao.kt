package com.chrrissoft.room.inventories.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.inventories.db.objects.Inventory
import com.chrrissoft.room.inventories.db.objects.InventoryWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventories WHERE product_id = :id")
    fun get(id: String) : Flow<InventoryWithRelationship>

    @Query("SELECT * FROM inventories")
    fun get() : Flow<List<InventoryWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Inventory>)

    @Delete
    suspend fun delete(data: List<Inventory>)
}
