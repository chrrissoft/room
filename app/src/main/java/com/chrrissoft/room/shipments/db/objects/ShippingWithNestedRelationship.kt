package com.chrrissoft.room.shipments.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship

data class ShippingWithNestedRelationship(
    @Embedded val shipping: Shipping,
    @Relation(entity = Carrier::class, parentColumn = "carrier_id", entityColumn = "id")
    val carrier: CarrierWithRelationship? = null,
    @Relation(entity = Order::class, parentColumn = "id", entityColumn = "shipping_id")
    val orders: List<OrderWithRelationship> = emptyList(),
)
