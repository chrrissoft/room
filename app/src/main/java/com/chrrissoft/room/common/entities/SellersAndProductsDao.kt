package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.SellersAndProducts

@Dao
interface SellersAndProductsDao {
    @Insert
    fun insert(data: List<SellersAndProducts>)

    @Delete
    fun delete(data: List<SellersAndProducts>)
}
