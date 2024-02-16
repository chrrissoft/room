package com.chrrissoft.room.sales.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {
    @Query("SELECT * FROM sales WHERE id = :id")
    fun get(id: String) : Flow<SaleWithRelationship>

    @Query("SELECT * FROM sales")
    fun get() : Flow<List<SaleWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Sale>)

    @Delete
    suspend fun delete(data: List<Sale>)
}
