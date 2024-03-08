package com.chrrissoft.room.sales.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.common.objects.CategoriesAndSales
import com.chrrissoft.room.common.objects.ProductsAndSales
import com.chrrissoft.room.common.objects.SalesAndPromotions
import com.chrrissoft.room.common.objects.SupplierAndSales
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
        associateBy = Junction(CategoriesAndSales::class, ("category_id"), ("category_id"))
    )
    val categories: List<Category>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSales::class, ("product_id"), ("product_id"))
    )
    val products: List<Product>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SalesAndPromotions::class, ("promotion_id"), ("promotion_id"))
    )
    val promotions: List<Promotion>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SupplierAndSales::class, ("supplier_id"), ("supplier_id"))
    )
    val suppliers: List<Supplier>,
)
