package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.CostumersAndProducts

@Dao
interface CostumersAndProductsDao {
    @Insert
    fun insert(data: List<CostumersAndProducts>)

    @Delete
    fun delete(data: List<CostumersAndProducts>)
}
