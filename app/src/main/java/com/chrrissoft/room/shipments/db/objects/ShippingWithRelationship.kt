package com.chrrissoft.room.shipments.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.sellers.db.objects.Seller

data class ShippingWithRelationship(
    @Embedded val shipping: Shipping,
    @Relation(parentColumn = "carrier_id", entityColumn = "id")
    val carrier: Carrier? = null,
    @Relation(parentColumn = "id", entityColumn = "shipping_id")
    val orders: List<Order> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndShipments::class, ("shipping_id"), ("city_id"))
    )
    val cities: List<City> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndShipments::class, ("shipping_id"), ("costumer_id"))
    )
    val costumers: List<Costumer> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndShipments::class, ("shipping_id"), ("seller_id"))
    )
    val sellers: List<Seller> = emptyList(),
)
