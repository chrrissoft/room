package com.chrrissoft.room.carriers.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship

data class CarrierWithNestedRelationship(
    @Embedded val carrier: Carrier,
    @Relation(entity = City::class, parentColumn = "city_id", entityColumn = "id")
    val city: CityWithRelationship = CityWithRelationship(City.invalid),
    @Relation(entity = Shipping::class, parentColumn = "id", entityColumn = "carrier_id")
    val shipments: List<ShippingWithRelationship> = emptyList(),
)
