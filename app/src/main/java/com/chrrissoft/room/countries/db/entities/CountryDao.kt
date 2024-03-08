package com.chrrissoft.room.countries.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.countries.db.objects.CountryNestedWithRelationship
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries WHERE id = :id")
    fun get(id: String) : Flow<CountryNestedWithRelationship>

    @Query("SELECT * FROM countries")
    fun get() : Flow<List<CountryWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Country>)

    @Delete
    suspend fun delete(data: List<Country>)
}
