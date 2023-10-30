package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.categories.Category
import com.chrrissoft.room.promotions.Promotion

@Entity(
    tableName = "categories_and_promotions",
    primaryKeys = ["category_id", "promotion_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Promotion::class,
            parentColumns = ["id"],
            childColumns = ["promotion_id"]),
    ]
)
data class CategoriesAndPromotions(
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "promotion_id") val promotionId: Long
)
