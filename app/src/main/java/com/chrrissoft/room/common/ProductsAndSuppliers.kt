package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.suppliers.db.objects.Supplier

@Entity(
    tableName = "products_and_suppliers",
    primaryKeys = ["product_id", "supplier_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Supplier::class,
            parentColumns = ["id"],
            childColumns = ["supplier_id"]),
    ]
)
data class ProductsAndSuppliers(
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "supplier_id") val supplierId: String,
)
