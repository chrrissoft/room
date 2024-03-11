package com.chrrissoft.room.sales.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.cross.db.objects.CategoriesAndSales
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.cross.db.objects.SalesAndPromotions
import com.chrrissoft.room.cross.db.objects.SuppliersAndSales
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.suppliers.db.objects.Supplier

data class SaleWithRelationship(
    @Embedded val sale: Sale,
    @Relation(parentColumn = "seller_id", entityColumn = "id")
    val seller: Seller = Seller.invalid,
    @Relation(parentColumn = "costumer_id", entityColumn = "id")
    val costumer: Costumer = Costumer.invalid,
    @Relation(parentColumn = "order_id", entityColumn = "id")
    val order: Order? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndSales::class, ("sale_id"), ("category_id"))
    )
    val categories: List<Category> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSales::class, ("sale_id"), ("product_id"))
    )
    val products: List<Product> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SalesAndPromotions::class, ("sale_id"), ("promotion_id"))
    )
    val promotions: List<Promotion> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SuppliersAndSales::class, ("sale_id"), ("supplier_id"))
    )
    val suppliers: List<Supplier> = emptyList(),
)
