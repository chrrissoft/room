package com.chrrissoft.room.products.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.cross.db.objects.ProductsAndSuppliers
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.suppliers.db.objects.Supplier
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship

data class ProductWithNestedRelationship(
    @Embedded val product: Product,
    @Relation(entity = Promotion::class, parentColumn = "promotion_id", entityColumn = "id")
    val promotion: PromotionWithRelationship? = null,
    @Relation(
        entity = Costumer::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndProducts::class, ("product_id"), ("costumer_id"))
    )
    val costumers: List<CostumerWithRelationship> = emptyList(),
    @Relation(
        entity = Sale::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSales::class, ("product_id"), ("sale_id"))
    )
    val sales: List<SaleWithRelationship> = emptyList(),
    @Relation(
        entity = Supplier::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSuppliers::class, ("product_id"), ("supplier_id"))
    )
    val suppliers: List<SupplierWithRelationship> = emptyList(),
    @Relation(
        entity = Seller::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndProducts::class, ("product_id"), ("seller_id"))
    )
    val sellers: List<SellerWithRelationship> = emptyList(),
)
