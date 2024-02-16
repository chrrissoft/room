package com.chrrissoft.room.categories.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.common.CategoriesAndOrders
import com.chrrissoft.room.common.CategoriesAndPromotions
import com.chrrissoft.room.common.CategoriesAndSales
import com.chrrissoft.room.common.SuppliersAndCategories
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.suppliers.db.objects.Supplier

data class CategoryWithRelationship(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndOrders::class, ("category_id"), ("order_id"))
    )
    val orders: List<Order>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndPromotions::class, ("category_id"), ("promotion_id"))
    )
    val promotions: List<Promotion>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndSales::class, ("category_id"), ("sale_id"))
    )
    val sales: List<Sale>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SuppliersAndCategories::class, ("category_id"), ("supplier_id"))
    )
    val suppliers: List<Supplier>
)
