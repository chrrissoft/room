package com.chrrissoft.room.common.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.chrrissoft.room.common.objects.CitiesAndSuppliers

@Dao
interface CitiesAndSuppliersDao {
    @Insert
    fun insert(data: List<CitiesAndSuppliers>)

    @Delete
    fun delete(data: List<CitiesAndSuppliers>)
}