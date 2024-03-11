package com.chrrissoft.room.sellers.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship

data class SellerWithNestedRelationship(
    @Embedded val seller: Seller,
    @Relation(entity = City::class, parentColumn = "city_id", entityColumn = "id")
    val city: CityWithRelationship = CityWithRelationship(City.invalid),
    @Relation(entity = Sale::class, parentColumn = "id", entityColumn = "seller_id")
    val sales: List<SaleWithRelationship> = emptyList(),
    @Relation(
        entity = Product::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndProducts::class, ("seller_id"), ("product_id"))
    )
    val products: List<ProductWithRelationship> = emptyList(),
    @Relation(
        entity = Shipping::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndShipments::class, ("seller_id"), ("shipping_id"))
    )
    val shipments: List<ShippingWithRelationship> = emptyList(),
)
