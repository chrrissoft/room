package com.chrrissoft.room.sellers.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.sales.db.objects.Sale

data class SellerWithRelationship(
    @Embedded val seller: Seller,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City = City.invalid,
    @Relation(parentColumn = "id", entityColumn = "seller_id")
    val sales: List<Sale> = emptyList(),
)
