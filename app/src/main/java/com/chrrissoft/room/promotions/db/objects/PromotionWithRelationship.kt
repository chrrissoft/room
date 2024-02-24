package com.chrrissoft.room.promotions.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.common.CategoriesAndPromotions
import com.chrrissoft.room.common.PromotionsAndOrders
import com.chrrissoft.room.common.SalesAndPromotions
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.sales.db.objects.Sale

data class PromotionWithRelationship(
    @Embedded val promotion: Promotion,
    @Relation(parentColumn = "id", entityColumn = "promotion_id")
    val products: List<Product> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SalesAndPromotions::class, ("promotion_id"), ("sale_id"))
        )
    val sales: List<Sale> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(PromotionsAndOrders::class, ("promotion_id"), ("order_id"))
        )
    val orders: List<Order> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndPromotions::class, ("promotion_id"), ("category_id"))
        )
    val categories: List<Category> = emptyList(),
)
