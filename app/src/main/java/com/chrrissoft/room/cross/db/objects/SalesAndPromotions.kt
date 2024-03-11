package com.chrrissoft.room.cross.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.sales.db.objects.Sale

@Entity(
    tableName = "sales_and_promotions",
    primaryKeys = ["sale_id", "promotion_id"],
    foreignKeys = [
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["sale_id"]),
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Promotion::class,
            parentColumns = ["id"],
            childColumns = ["promotion_id"]),
    ]
)
data class SalesAndPromotions(
    @ColumnInfo(name = "sale_id") val salesId: String,
    @ColumnInfo(name = "promotion_id") val promotionId: String,
)
