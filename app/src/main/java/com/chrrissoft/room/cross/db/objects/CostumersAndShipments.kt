package com.chrrissoft.room.cross.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.shipments.db.objects.Shipping

@Entity(
    tableName = "costumers_and_shipments",
    primaryKeys = ["costumer_id", "shipping_id"],
    foreignKeys = [
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Costumer::class,
            parentColumns = ["id"],
            childColumns = ["costumer_id"]),
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"]),
    ]
)
data class CostumersAndShipments(
    @ColumnInfo(name = "costumer_id") val costumerId: String,
    @ColumnInfo(name = "shipping_id") val shippingId: String,
)
