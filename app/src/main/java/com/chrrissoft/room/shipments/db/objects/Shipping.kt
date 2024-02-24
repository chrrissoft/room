package com.chrrissoft.room.shipments.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.carriers.db.objects.Carrier

@Entity(tableName = "shipments", foreignKeys = [ForeignKey(
    entity = Carrier::class,
    parentColumns = ["id"],
    childColumns = ["carrier_id"],
    onUpdate = ForeignKey.CASCADE,
    onDelete = ForeignKey.RESTRICT,
)])
data class Shipping(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "state") val state: ShippingState = ShippingState.PENDING,
    @ColumnInfo(name = "carrier_id") val carrierId: String = "",
) {
    enum class ShippingState {
        PENDING, ON_THE_WAY, DELIVERED
    }

    companion object {
        val invalid = Shipping("")
    }
}
