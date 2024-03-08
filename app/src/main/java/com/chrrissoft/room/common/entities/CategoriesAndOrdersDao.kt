package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.chrrissoft.room.common.objects.CategoriesAndOrders

@Dao
interface CategoriesAndOrdersDao {
    @Insert
    fun insert(data: List<CategoriesAndOrders>)

    @Delete
    fun delete(data: List<CategoriesAndOrders>)
}
