package com.chrrissoft.room.promotions.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface PromotionDao {
    @Query("SELECT * FROM promotions WHERE id = :id")
    fun get(id: String) : Flow<PromotionWithRelationship>

    @Query("SELECT * FROM promotions")
    fun get() : Flow<List<PromotionWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Promotion>)

    @Delete
    suspend fun delete(data: List<Promotion>)
}
