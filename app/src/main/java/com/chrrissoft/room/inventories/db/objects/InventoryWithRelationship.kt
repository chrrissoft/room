package com.chrrissoft.room.inventories.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.products.db.objects.Product

data class InventoryWithRelationship(
    @Embedded val inventory: Inventory,
    @Relation(parentColumn = "id", entityColumn = "product_id")
    val products: List<Product>,
)
