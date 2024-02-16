package com.chrrissoft.room.reviews.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.products.db.objects.Product

data class ReviewWithRelationship(
    @Embedded val review: Review,
    @Relation(parentColumn = "product_id", entityColumn = "id")
    val product: Product,
    @Relation(parentColumn = "costumer_id", entityColumn = "id")
    val costumer: Costumer,
)
