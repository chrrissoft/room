package com.chrrissoft.room.products.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.inventories.db.objects.Inventory
import com.chrrissoft.room.promotions.db.objects.Promotion


@Entity(
    "products", foreignKeys = [
        ForeignKey(
            entity = Inventory::class,
            parentColumns = ["product_id"],
            childColumns = ["inventory_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = Promotion::class,
            parentColumns = ["id"],
            childColumns = ["promotion_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.RESTRICT,
        ),
    ]
)
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "inventory_id") val inventoryId: String,
    @ColumnInfo(name = "promotion_id") val promotionId: String,
)
