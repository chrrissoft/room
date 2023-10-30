package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.promotions.Promotion
import com.chrrissoft.room.sales.Sale

@Entity(
    tableName = "sales_and_promotions",
    primaryKeys = ["sales_id", "promotion_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["sale_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Promotion::class,
            parentColumns = ["id"],
            childColumns = ["promotion_id"]),
    ]
)
data class SalesAndPromotions(
    @ColumnInfo(name = "sale_id") val salesId: Long,
    @ColumnInfo(name = "promotion_id") val promotionId: Long,
)
