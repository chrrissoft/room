package com.chrrissoft.room.orders.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.shipments.db.objects.Shipping

data class OrderWithRelationship(
    @Embedded val order: Order,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City = City.invalid,
    @Relation(parentColumn = "shipping_id", entityColumn = "id")
    val shipping: Shipping = Shipping.invalid,
//    @Relation(parentColumn = "id", entityColumn = "id")
//    val sale: SaleWithRelationship,
)
