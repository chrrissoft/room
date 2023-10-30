package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.products.Product
import com.chrrissoft.room.promotions.Promotion

@Entity(
    tableName = "products_and_promotions",
    primaryKeys = ["product_id", "promotion_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Promotion::class,
            parentColumns = ["id"],
            childColumns = ["promotion_id"]),
    ]
)
data class ProductsAndPromotions(
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "promotion_id") val promotionId: Long,
)
