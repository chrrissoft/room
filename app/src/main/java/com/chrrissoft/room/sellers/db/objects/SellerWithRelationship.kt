package com.chrrissoft.room.sellers.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.shipments.db.objects.Shipping

data class SellerWithRelationship(
    @Embedded val seller: Seller,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City = City.invalid,
    @Relation(parentColumn = "id", entityColumn = "seller_id")
    val sales: List<Sale> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndProducts::class, ("seller_id"), ("product_id"))
    )
    val products: List<Product> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndShipments::class, ("seller_id"), ("shipping_id"))
    )
    val shipments: List<Shipping> = emptyList(),
)
