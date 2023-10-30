package com.chrrissoft.room.costumers

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface CostumersDao {

    @Insert
    fun insert(vararg costumer: Costumer) : List<Boolean>

    @Update
    fun update(vararg costumer: Costumer) : List<Long>

    @Delete
    fun delete(vararg costumer: Costumer) : List<Long>

    @Query("SELECT * FROM costumer")
    fun all() : Flow<Costumer>
}
