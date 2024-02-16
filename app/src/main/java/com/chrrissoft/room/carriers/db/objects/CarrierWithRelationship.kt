package com.chrrissoft.room.carriers.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.shipments.db.objects.Shipping

data class CarrierWithRelationship(
    @Embedded val carrier: Carrier,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City,
    @Relation(parentColumn = "id", entityColumn = "carrier_id")
    val shipments: List<Shipping>,
)
