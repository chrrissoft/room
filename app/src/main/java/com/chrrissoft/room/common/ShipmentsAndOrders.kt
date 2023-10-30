package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.orders.Order
import com.chrrissoft.room.shipping.Shipping

@Entity(
    tableName = "shipments_and_orders",
    primaryKeys = ["shipping_id", "order_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["order_id"]),
    ]
)
data class ShipmentsAndOrders(
    @ColumnInfo(name = "shipping_id") val shippingId: Long,
    @ColumnInfo(name = "order_id") val orderId: Long
)
