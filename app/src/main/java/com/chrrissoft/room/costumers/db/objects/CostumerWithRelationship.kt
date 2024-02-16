package com.chrrissoft.room.costumers.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.common.CostumersAndProducts
import com.chrrissoft.room.common.CostumersAndShipments
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.reviews.db.objects.Review
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.shipments.db.objects.Shipping

data class CostumerWithRelationship(
    @Embedded val costumer: Costumer,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City,
    @Relation(parentColumn = "id", entityColumn = "order_id")
    val orders: List<Order>,
    @Relation(parentColumn = "id", entityColumn = "review_id")
    val reviews: List<Review>,
    @Relation(parentColumn = "id", entityColumn = "sale_id")
    val sales: List<Sale>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndProducts::class, ("product_id"), ("product_id"))
    )
    val product: List<Product>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndShipments::class, ("product_id"), ("shipping_id"))
    )
    val shipments: List<Shipping>,
)
