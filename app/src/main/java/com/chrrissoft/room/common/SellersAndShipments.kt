package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.sellers.Seller
import com.chrrissoft.room.shipping.Shipping

@Entity(
    tableName = "sellers_and_shipments",
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Seller::class,
            parentColumns = ["id"],
            childColumns = ["seller_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"]),
    ]
)
data class SellersAndShipments(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "seller_id") val sellerId: Long,
    @ColumnInfo(name = "shipping_id") val shippingId: Long,
)
