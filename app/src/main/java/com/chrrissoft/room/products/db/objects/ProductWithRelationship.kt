package com.chrrissoft.room.products.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.promotions.db.objects.Promotion

data class ProductWithRelationship(
    @Embedded val product: Product,
    @Relation(parentColumn = "promotion_id", entityColumn = "id")
    val promotion: Promotion? = null,
)
