package com.chrrissoft.room.common.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.sales.db.objects.Sale

@Entity(
    tableName = "products_and_sales",
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["sale_id"]),
    ]
)
data class ProductsAndSales(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "sale_id") val saleId: String,
)
