package com.chrrissoft.room.sellers.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship

data class SellerWithNestedRelationship(
    @Embedded val seller: Seller,
    @Relation(entity = City::class, parentColumn = "city_id", entityColumn = "id")
    val city: CityWithRelationship = CityWithRelationship(City.invalid),
    @Relation(entity = Sale::class, parentColumn = "id", entityColumn = "seller_id")
    val sales: List<SaleWithRelationship> = emptyList(),
)
