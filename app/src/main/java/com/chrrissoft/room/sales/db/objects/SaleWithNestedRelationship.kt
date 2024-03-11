package com.chrrissoft.room.sales.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.cross.db.objects.CategoriesAndSales
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.cross.db.objects.SalesAndPromotions
import com.chrrissoft.room.cross.db.objects.SuppliersAndSales
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.suppliers.db.objects.Supplier
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
        entity = Category::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CategoriesAndSales::class, ("sale_id"), ("category_id"))
    )
    val categories: List<CategoryWithRelationship> = emptyList(),
    @Relation(
        entity = Product::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSales::class, ("sale_id"), ("product_id"))
    )
    val products: List<ProductWithRelationship> = emptyList(),
    @Relation(
        entity = Promotion::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SalesAndPromotions::class, ("sale_id"), ("promotion_id"))
    )
    val promotions: List<PromotionWithRelationship> = emptyList(),
    @Relation(
        entity = Supplier::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SuppliersAndSales::class, ("sale_id"), ("supplier_id"))
    )
    val suppliers: List<SupplierWithRelationship> = emptyList(),
)
