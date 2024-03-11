package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.chrrissoft.room.cross.db.objects.SellersAndProducts

@Dao
interface SellersAndProductsDao {
    @Query("SELECT * FROM sellers_and_products LIMIT 1")
    suspend fun get() : SellersAndProducts

    @Insert
    suspend fun insert(data: List<SellersAndProducts>)

    @Delete
    suspend fun delete(data: List<SellersAndProducts>)
}
