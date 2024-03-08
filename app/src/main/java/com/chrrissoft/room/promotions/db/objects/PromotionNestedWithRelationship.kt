package com.chrrissoft.room.promotions.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.common.objects.CategoriesAndPromotions
import com.chrrissoft.room.common.objects.PromotionsAndOrders
import com.chrrissoft.room.common.objects.SalesAndPromotions
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship

data class PromotionNestedWithRelationship(
    @Embedded val promotion: Promotion,
    @Relation(entity = Product::class, parentColumn = "id", entityColumn = "promotion_id")
    val products: List<ProductWithRelationship> = emptyList(),
    @Relation(
        entity = Sale::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SalesAndPromotions::class, ("promotion_id"), ("sale_id"))
    )
    val sales: List<SaleWithRelationship> = emptyList(),
    @Relation(
        entity = Order::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(PromotionsAndOrders::class, ("promotion_id"), ("order_id"))
    )
    val orders: List<OrderWithRelationship> = emptyList(),
    @Relation(
        entity = Category::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndPromotions::class, ("promotion_id"), ("category_id"))
    )
    val categories: List<CategoryWithRelationship> = emptyList(),
)
