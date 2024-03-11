package com.chrrissoft.room.costumers.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship

data class CostumerWithNestedRelationship(
    @Embedded val costumer: Costumer,
    @Relation(entity = City::class, parentColumn = "city_id", entityColumn = "id")
    val city: CityWithRelationship? = null,
    @Relation(entity = Sale::class, parentColumn = "id", entityColumn = "costumer_id")
    val sales: List<SaleWithRelationship> = emptyList(),
    @Relation(
        entity = Product::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndProducts::class, ("costumer_id"), ("product_id"))
    )
    val products: List<ProductWithRelationship> = emptyList(),
    @Relation(
        entity = Shipping::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndShipments::class, ("costumer_id"), ("shipping_id"))
    )
    val shipments: List<ShippingWithRelationship> = emptyList(),
)
