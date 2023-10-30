package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.sales.Sale
import com.chrrissoft.room.suppliers.Supplier

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
    @ColumnInfo(name = "supplier_id") val supplierId: Long,
    @ColumnInfo(name = "sale_id") val saleId: Long,
)
