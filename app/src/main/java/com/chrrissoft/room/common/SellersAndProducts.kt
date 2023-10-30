package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.products.Product
import com.chrrissoft.room.sellers.Seller

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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "seller_id") val sellerId: Long,
    @ColumnInfo(name = "product_id") val productId: Long,
)
