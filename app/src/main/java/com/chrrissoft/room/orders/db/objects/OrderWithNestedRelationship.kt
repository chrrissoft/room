package com.chrrissoft.room.orders.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship

data class OrderWithNestedRelationship(
    @Embedded val order: Order,
    @Relation(entity = Sale::class, parentColumn = "id", entityColumn = "id")
    val sale: SaleWithRelationship = SaleWithRelationship(Sale.invalid),
    @Relation(entity = City::class, parentColumn = "city_id", entityColumn = "id")
    val city: CityWithRelationship = CityWithRelationship(City.invalid),
    @Relation(entity = Shipping::class, parentColumn = "shipping_id", entityColumn = "id")
    val shipping: ShippingWithRelationship? = null,
)
