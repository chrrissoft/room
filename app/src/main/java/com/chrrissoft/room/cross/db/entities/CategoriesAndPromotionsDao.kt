package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.cross.db.objects.CategoriesAndPromotions

@Dao
interface CategoriesAndPromotionsDao {
    @Insert
    suspend fun insert(data: List<CategoriesAndPromotions>)

    @Delete
    suspend fun delete(data: List<CategoriesAndPromotions>)
}
