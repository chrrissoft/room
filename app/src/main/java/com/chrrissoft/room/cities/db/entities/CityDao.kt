package com.chrrissoft.room.cities.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {
    @Query("SELECT * FROM cities WHERE id = :id")
    fun get(id: String) : Flow<CityWithRelationship>

    @Query("SELECT * FROM cities")
    fun get() : Flow<List<CityWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<City>)

    @Delete
    suspend fun delete(data: List<City>)
}
