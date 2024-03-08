package com.chrrissoft.room.sales.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.common.objects.CategoriesAndSales
import com.chrrissoft.room.common.objects.ProductsAndSales
import com.chrrissoft.room.common.objects.SalesAndPromotions
import com.chrrissoft.room.common.objects.SupplierAndSales
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship

data class SaleWithNestedRelationship(
    @Embedded val sale: Sale,
    @Relation(entity = Seller::class, parentColumn = "seller_id", entityColumn = "id")
    val seller: SellerWithRelationship = SellerWithRelationship(Seller.invalid),
    @Relation(entity = Costumer::class, parentColumn = "costumer_id", entityColumn = "id")
    val costumer: CostumerWithRelationship = CostumerWithRelationship(Costumer.invalid),
    @Relation(entity = Order::class, parentColumn = "order_id", entityColumn = "id")
    val order: OrderWithRelationship? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndSales::class, ("category_id"), ("category_id"))
    )
    val categories: List<CategoryWithRelationship> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSales::class, ("product_id"), ("product_id"))
    )
    val products: List<ProductWithRelationship> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SalesAndPromotions::class, ("promotion_id"), ("promotion_id"))
    )
    val promotions: List<PromotionWithRelationship> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SupplierAndSales::class, ("supplier_id"), ("supplier_id"))
    )
    val suppliers: List<SupplierWithRelationship> = emptyList(),
)
