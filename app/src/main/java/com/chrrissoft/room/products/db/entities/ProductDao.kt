package com.chrrissoft.room.products.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithNestedRelationship
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE id = :id")
    fun get(id: String) : Flow<ProductWithNestedRelationship>

    @Query("SELECT * FROM products")
    fun get() : Flow<List<ProductWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Product>)

    @Delete
    suspend fun delete(data: List<Product>)
}
