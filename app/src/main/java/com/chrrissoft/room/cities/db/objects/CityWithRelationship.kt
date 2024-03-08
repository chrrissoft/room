package com.chrrissoft.room.cities.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.common.objects.CitiesAndShipments
import com.chrrissoft.room.common.objects.CitiesAndSuppliers
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.suppliers.db.objects.Supplier

data class CityWithRelationship(
    @Embedded val city: City,
    @Relation(parentColumn = "country_id", entityColumn = "id")
    val country: Country = Country.invalid,
    @Relation(parentColumn = "id", entityColumn = "city_id")
    val carriers: List<Carrier> = emptyList(),
    @Relation(parentColumn = "id", entityColumn = "city_id")
    val costumers: List<Costumer> = emptyList(),
    @Relation(parentColumn = "id", entityColumn = "city_id")
    val orders: List<Order> = emptyList(),
    @Relation(parentColumn = "id", entityColumn = "city_id")
    val sellers: List<Seller> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndShipments::class, ("city_id"), ("shipping_id"))
    )
    val shipments: List<Shipping> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndSuppliers::class, ("city_id"), ("supplier_id"))
    )
    val suppliers: List<Supplier> = emptyList(),
)
