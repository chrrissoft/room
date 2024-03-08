package com.chrrissoft.room.sellers.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithNestedRelationship
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface SellerDao {
    @Query("SELECT * FROM sellers WHERE id = :id")
    fun get(id: String) : Flow<SellerWithNestedRelationship>

    @Query("SELECT * FROM sellers")
    fun get() : Flow<List<SellerWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Seller>)

    @Delete
    suspend fun delete(data: List<Seller>)
}
