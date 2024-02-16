package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.suppliers.db.objects.Supplier

@Entity(
    tableName = "suppliers_and_categories",
    primaryKeys = ["supplier_id", "category_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Supplier::class,
            parentColumns = ["id"],
            childColumns = ["supplier_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["category_id"]),
    ]
)
data class SuppliersAndCategories(
    @ColumnInfo(name = "supplier_id") val supplierId: String,
    @ColumnInfo(name = "category_id") val categoryId: String,
)
