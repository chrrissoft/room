package com.chrrissoft.room.products.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship

data class ProductWithNestedRelationship(
    @Embedded val product: Product,
    @Relation(entity = Promotion::class, parentColumn = "promotion_id", entityColumn = "id")
    val promotion: PromotionWithRelationship? = null,
)
