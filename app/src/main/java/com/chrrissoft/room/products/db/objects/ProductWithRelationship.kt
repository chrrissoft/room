package com.chrrissoft.room.products.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.cross.db.objects.ProductsAndSuppliers
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.suppliers.db.objects.Supplier

data class ProductWithRelationship(
    @Embedded val product: Product,
    @Relation(parentColumn = "promotion_id", entityColumn = "id")
    val promotion: Promotion? = null,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CostumersAndProducts::class, ("product_id"), ("costumer_id"))
    )
    val costumers: List<Costumer> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSales::class, ("product_id"), ("sale_id"))
    )
    val sales: List<Sale> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSuppliers::class, ("product_id"), ("supplier_id"))
    )
    val suppliers: List<Supplier> = emptyList(),
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SellersAndProducts::class, ("product_id"), ("seller_id"))
    )
    val seller: List<Seller> = emptyList(),
)
