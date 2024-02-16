package com.chrrissoft.room.orders.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.common.CategoriesAndOrders
import com.chrrissoft.room.common.ProductsAndOrders
import com.chrrissoft.room.common.PromotionsAndOrders
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.shipments.db.objects.Shipping

data class OrderWithRelationship(
    @Embedded val order: Order,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City,
    @Relation(parentColumn = "shipping_id", entityColumn = "id")
    val shipping: Shipping,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndOrders::class, ("order_id"), ("category_id"))
    )
    val categories: List<Category>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndOrders::class, ("order_id"), ("product_id"))
    )
    val products: List<Product>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(PromotionsAndOrders::class, ("order_id"), ("promotion_id"))
    )
    val promotions: List<Promotion>,
)
