package com.chrrissoft.room.orders.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.common.objects.CategoriesAndOrders
import com.chrrissoft.room.common.objects.ProductsAndOrders
import com.chrrissoft.room.common.objects.PromotionsAndOrders
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.shipments.db.objects.Shipping

data class OrderWithRelationship(
    @Embedded val order: Order,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City = City.invalid,
    @Relation(parentColumn = "shipping_id", entityColumn = "id")
    val shipping: Shipping? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndOrders::class, ("category_id"), ("category_id"))
    )
    val categories: List<Category> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndOrders::class, ("product_id"), ("product_id"))
    )
    val products: List<Product> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(PromotionsAndOrders::class, ("promotion_id"), ("promotion_id"))
    )
    val promotions: List<Product> = emptyList(),
)
// TODO view with sales
