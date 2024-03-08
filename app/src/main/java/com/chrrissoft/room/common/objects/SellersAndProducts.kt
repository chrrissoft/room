package com.chrrissoft.room.common.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.sellers.db.objects.Seller

@Entity(
    tableName = "sellers_and_products",
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Seller::class,
            parentColumns = ["id"],
            childColumns = ["seller_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
    ]
)
data class SellersAndProducts(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "seller_id") val sellerId: String,
    @ColumnInfo(name = "product_id") val productId: String,
)
