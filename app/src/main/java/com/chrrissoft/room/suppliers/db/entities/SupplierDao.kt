package com.chrrissoft.room.suppliers.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithNestedRelationship
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {
    @Query("SELECT * FROM suppliers WHERE id = :id")
    fun get(id: String) : Flow<SupplierWithNestedRelationship>

    @Query("SELECT * FROM suppliers")
    fun get() : Flow<List<SupplierWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Supplier>)

    @Delete
    suspend fun delete(data: List<Supplier>)
}
