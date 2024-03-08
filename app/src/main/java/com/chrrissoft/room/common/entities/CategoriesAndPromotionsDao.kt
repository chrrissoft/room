package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.CategoriesAndPromotions

@Dao
interface CategoriesAndPromotionsDao {
    @Insert
    fun insert(data: List<CategoriesAndPromotions>)

    @Delete
    fun delete(data: List<CategoriesAndPromotions>)
}
