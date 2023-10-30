package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.orders.Order
import com.chrrissoft.room.promotions.Promotion

@Entity(
    tableName = "promotions_and_orders",
    primaryKeys = ["promotion_id", "order_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Promotion::class,
            parentColumns = ["id"],
            childColumns = ["promotion_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["order_id"]),
    ]
)
data class PromotionsAndOrders(
    @ColumnInfo(name = "promotion_id") val promotionId: Long,
    @ColumnInfo(name = "order_id") val orderId: Long,
)
