package com.chrrissoft.room.shipments.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.sales.db.objects.Sale

data class ShippingWithRelationship(
    @Embedded val shipping: Shipping,
    @Relation(parentColumn = "carrier_id", entityColumn = "id")
    val carrier: Carrier,
    @Relation(parentColumn = "id", entityColumn = "shipping_id")
    val orders: List<Order>,
    @Relation(parentColumn = "id", entityColumn = "shipping_id")
    val sales: List<Sale>,
)
