package com.chrrissoft.room.common.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.shipments.db.objects.Shipping

@Entity(
    tableName = "products_and_shipments",
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"]),
    ]
)
data class ProductsAndShipments(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "shipping_id") val shippingId: String,
)
