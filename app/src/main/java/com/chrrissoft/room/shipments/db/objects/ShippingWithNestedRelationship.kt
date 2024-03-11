package com.chrrissoft.room.shipments.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship

data class ShippingWithNestedRelationship(
    @Embedded val shipping: Shipping,
    @Relation(entity = Carrier::class, parentColumn = "carrier_id", entityColumn = "id")
    val carrier: CarrierWithRelationship? = null,
    @Relation(entity = Order::class, parentColumn = "id", entityColumn = "shipping_id")
    val orders: List<OrderWithRelationship> = emptyList(),
    @Relation(
        entity = City::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndShipments::class, ("shipping_id"), ("city_id"))
    )
    val cities: List<CityWithRelationship> = emptyList(),
    @Relation(
        entity = Costumer::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndShipments::class, ("shipping_id"), ("costumer_id"))
    )
    val costumers: List<CostumerWithRelationship> = emptyList(),
    @Relation(
        entity = Seller::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndShipments::class, ("shipping_id"), ("seller_id"))
    )
    val sellers: List<SellerWithRelationship> = emptyList(),
)
