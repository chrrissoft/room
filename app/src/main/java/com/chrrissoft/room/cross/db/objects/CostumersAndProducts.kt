package com.chrrissoft.room.cross.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.products.db.objects.Product

@Entity(
    tableName = "costumers_and_products",
    foreignKeys = [
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Costumer::class,
            parentColumns = ["id"],
            childColumns = ["costumer_id"]),
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
    ]
)
data class CostumersAndProducts(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "costumer_id") val costumerId: String,
    @ColumnInfo(name = "product_id") val productId: String,
)
