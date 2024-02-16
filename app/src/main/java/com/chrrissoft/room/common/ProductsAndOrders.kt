package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.products.db.objects.Product

@Entity(
    tableName = "products_and_orders",
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["order_id"]),
    ]
)
data class ProductsAndOrders(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "order_id") val orderId: String,
)
