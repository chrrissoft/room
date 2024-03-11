package com.chrrissoft.room.cities.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments
import com.chrrissoft.room.cross.db.objects.CitiesAndSuppliers
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship

data class CityWithNestedRelationship(
    @Embedded val city: City,
    @Relation(parentColumn = "country_id", entityColumn = "id")
    val country: Country = Country.invalid,
    @Relation(entity = Carrier::class, parentColumn = "id", entityColumn = "city_id")
    val carriers: List<CarrierWithRelationship> = emptyList(),
    @Relation(entity = Costumer::class, parentColumn = "id", entityColumn = "city_id")
    val costumers: List<CostumerWithRelationship> = emptyList(),
    @Relation(entity = Order::class, parentColumn = "id", entityColumn = "city_id")
    val orders: List<OrderWithRelationship> = emptyList(),
    @Relation(entity = Seller::class, parentColumn = "id", entityColumn = "city_id")
    val sellers: List<SellerWithRelationship> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = Shipping::class,
        associateBy = Junction(CitiesAndShipments::class, ("city_id"), ("shipping_id"))
    )
    val shipments: List<ShippingWithRelationship> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = Supplier::class,
        associateBy = Junction(CitiesAndSuppliers::class, ("city_id"), ("supplier_id"))
    )
    val suppliers: List<SupplierWithRelationship> = emptyList(),
)
