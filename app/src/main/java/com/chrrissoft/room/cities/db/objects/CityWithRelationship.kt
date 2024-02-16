package com.chrrissoft.room.cities.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.common.CitiesAndShipments
import com.chrrissoft.room.common.CitiesAndSuppliers
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.suppliers.db.objects.Supplier

data class CityWithRelationship(
    @Embedded val city: City,
    @Relation(parentColumn = "country_id", entityColumn = "id")
    val country: Country,
    @Relation(parentColumn = "id", entityColumn = "city_id")
    val carriers: List<Carrier>,
    @Relation(parentColumn = "id", entityColumn = "costumer_id")
    val costumers: List<Costumer>,
    @Relation(parentColumn = "id", entityColumn = "order_id")
    val orders: List<Order>,
    @Relation(parentColumn = "id", entityColumn = "seller_id")
    val sellers: List<Seller>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndShipments::class, ("city_id"), ("shipping_id"))
    )
    val shipments: List<Shipping>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndSuppliers::class, ("city_id"), ("suppliers_id"))
    )
    val suppliers: List<Supplier>,
)
