package com.chrrissoft.room.carriers.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface CarrierDao {
    @Query("SELECT * FROM carriers WHERE id = :id")
    fun get(id: String) : Flow<CarrierWithRelationship>

    @Query("SELECT * FROM carriers")
    fun get() : Flow<List<CarrierWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Carrier>)

    @Delete
    suspend fun delete(data: List<Carrier>)
}
