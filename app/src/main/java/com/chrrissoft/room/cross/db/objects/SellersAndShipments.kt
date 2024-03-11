package com.chrrissoft.room.cross.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.shipments.db.objects.Shipping

@Entity(
    tableName = "sellers_and_shipments",
    primaryKeys = ["seller_id", "shipping_id"],
    foreignKeys = [
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Seller::class,
            parentColumns = ["id"],
            childColumns = ["seller_id"]),
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"]),
    ]
)
data class SellersAndShipments(
    @ColumnInfo(name = "seller_id") val sellerId: String,
    @ColumnInfo(name = "shipping_id") val shippingId: String,
)
