package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.categories.Category
import com.chrrissoft.room.sales.Sale

@Entity(
    tableName = "categories_and_sales",
    primaryKeys = ["category_id", "sale_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["sale_id"]),
    ]
)
data class CategoriesAndSales(
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "sale_id") val saleId: Long,
)
