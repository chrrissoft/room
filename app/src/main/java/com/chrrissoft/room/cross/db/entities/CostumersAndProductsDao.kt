package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts

@Dao
interface CostumersAndProductsDao {
    @Query("SELECT * FROM costumers_and_products LIMIT 1")
    suspend fun get() : CostumersAndProducts

    @Insert
    suspend fun insert(data: List<CostumersAndProducts>)

    @Delete
    suspend fun delete(data: List<CostumersAndProducts>)
}
