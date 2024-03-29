package com.chrrissoft.room.costumers.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.shipments.db.objects.Shipping

data class CostumerWithRelationship(
    @Embedded val costumer: Costumer,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City? = null,
    @Relation(parentColumn = "id", entityColumn = "costumer_id")
    val sales: List<Sale> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndProducts::class, ("costumer_id"), ("product_id"))
    )
    val product: List<Product> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndShipments::class, ("costumer_id"), ("shipping_id"))
    )
    val shipments: List<Shipping> = emptyList(),
)
