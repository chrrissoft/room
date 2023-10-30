package com.chrrissoft.room.shipping

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.carriers.Carrier

@Entity(tableName = "shipments", foreignKeys = [ForeignKey(
    entity = Carrier::class,
    parentColumns = ["id"],
    childColumns = ["carrier_id"]
)])
data class Shipping(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "state") val state: ShippingState,
    @ColumnInfo(name = "carrier_id") val carrierId: Long,
) {
    enum class ShippingState {
        PENDING, ON_THE_WAY, DELIVERED
    }
}
