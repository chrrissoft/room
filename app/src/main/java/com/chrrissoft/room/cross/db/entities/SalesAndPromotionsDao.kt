package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.SalesAndPromotions

@Dao
interface SalesAndPromotionsDao {
    @Insert
    suspend fun insert(data: List<SalesAndPromotions>)

    @Delete
    suspend fun delete(data: List<SalesAndPromotions>)
}
