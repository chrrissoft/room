package com.chrrissoft.room.suppliers.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.common.CitiesAndSuppliers
import com.chrrissoft.room.common.ProductsAndSuppliers
import com.chrrissoft.room.common.SupplierAndSales
import com.chrrissoft.room.common.SuppliersAndCategories
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.sales.db.objects.Sale

data class SupplierWithRelationship(
    @Embedded val supplier: Supplier,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndSuppliers::class, ("supplier_id"), ("city_id"))
    )
    val cities: List<City>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSuppliers::class, ("supplier_id"), ("product_id"))
    )
    val products: List<Product>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SupplierAndSales::class, ("supplier_id"), ("sale_id"))
    )
    val sales: List<Sale>,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SuppliersAndCategories::class, ("supplier_id"), ("category_id"))
    )
    val categories: List<Category>,
)
