package com.chrrissoft.room.costumers.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface CostumerDao {
    @Query("SELECT * FROM costumers WHERE id = :id")
    fun get(id: String) : Flow<CostumerWithRelationship>

    @Query("SELECT * FROM costumers")
    fun get() : Flow<List<CostumerWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Costumer>)

    @Delete
    suspend fun delete(data: List<Costumer>)
}
