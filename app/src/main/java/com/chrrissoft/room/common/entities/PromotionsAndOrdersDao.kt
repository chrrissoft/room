package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.PromotionsAndOrders

@Dao
interface PromotionsAndOrdersDao {
    @Insert
    fun insert(data: List<PromotionsAndOrders>)

    @Delete
    fun delete(data: List<PromotionsAndOrders>)
}
