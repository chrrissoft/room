package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.costumers.Costumer
import com.chrrissoft.room.products.Product

@Entity(
    tableName = "costumers_and_products",
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Costumer::class,
            parentColumns = ["id"],
            childColumns = ["costumer_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
    ]
)
data class CostumersAndProducts(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "costumer_id") val costumerId: Long,
    @ColumnInfo(name = "product_id") val productId: Long,
)
