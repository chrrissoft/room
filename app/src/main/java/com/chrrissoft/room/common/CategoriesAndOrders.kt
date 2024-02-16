package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.orders.db.objects.Order

@Entity(
    tableName = "categories_and_orders",
    primaryKeys = ["category_id", "order_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]
        ),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["order_id"],
        ),
    ],
)
data class CategoriesAndOrders(
    @ColumnInfo(name = "category_id") val categoryId: String,
    @ColumnInfo(name = "order_id") val orderId: String,
)
