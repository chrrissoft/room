package com.chrrissoft.room.products.db.objects

import androidx.room.Embedded

data class ProductWithRelationship(
    @Embedded val product: Product,
)
