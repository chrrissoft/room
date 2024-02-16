package com.chrrissoft.room.payments.db.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chrrissoft.room.payments.db.objects.Payment
import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payments WHERE id = :id")
    fun get(id: String) : Flow<PaymentWithRelationship>

    @Query("SELECT * FROM payments")
    fun get() : Flow<List<PaymentWithRelationship>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Payment>)

    @Delete
    suspend fun delete(data: List<Payment>)
}
