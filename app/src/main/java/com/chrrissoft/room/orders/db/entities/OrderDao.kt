package com.chrrissoft.room.orders.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders WHERE id = :id")
    fun get(id: String) : Flow<OrderWithRelationship>

    @Query("SELECT * FROM orders")
    fun get() : Flow<List<OrderWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Order>)

    @Delete
    suspend fun delete(data: List<Order>)
}
