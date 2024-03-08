package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.SalesAndPromotions

@Dao
interface SalesAndPromotionsDao {
    @Insert
    fun insert(data: List<SalesAndPromotions>)

    @Delete
    fun delete(data: List<SalesAndPromotions>)
}
