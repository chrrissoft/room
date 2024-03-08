package com.chrrissoft.room.common.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.suppliers.db.objects.Supplier

@Entity(
    tableName = "supplier_and_sales",
    primaryKeys = ["supplier_id", "sale_id"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Supplier::class,
            parentColumns = ["id"],
            childColumns = ["supplier_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["sale_id"]),
    ]
)
data class SupplierAndSales(
    @ColumnInfo(name = "supplier_id") val supplierId: String,
    @ColumnInfo(name = "sale_id") val saleId: String,
)
