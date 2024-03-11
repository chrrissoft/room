package com.chrrissoft.room.cross.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.chrrissoft.room.cross.db.objects.CitiesAndSuppliers

@Dao
interface CitiesAndSuppliersDao {
    @Query("SELECT * FROM cities_and_suppliers LIMIT 1")
    suspend fun get() : CitiesAndSuppliers

    @Insert
    suspend fun insert(data: List<CitiesAndSuppliers>)

    @Delete
    suspend fun delete(data: List<CitiesAndSuppliers>)
}
