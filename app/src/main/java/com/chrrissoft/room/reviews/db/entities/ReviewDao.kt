package com.chrrissoft.room.reviews.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.reviews.db.objects.Review
import com.chrrissoft.room.reviews.db.objects.ReviewWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE id = :id")
    fun get(id: String) : Flow<ReviewWithRelationship>

    @Query("SELECT * FROM reviews")
    fun get() : Flow<List<ReviewWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Review>)

    @Delete
    suspend fun delete(data: List<Review>)
}
