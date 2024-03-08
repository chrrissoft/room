package com.chrrissoft.room.suppliers.db.objects

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.common.objects.CitiesAndSuppliers
import com.chrrissoft.room.common.objects.ProductsAndSuppliers
import com.chrrissoft.room.common.objects.SupplierAndSales
import com.chrrissoft.room.common.objects.SuppliersAndCategories
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship

data class SupplierWithNestedRelationship(
    @Embedded val supplier: Supplier,
    @Relation(
        entity = City::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(CitiesAndSuppliers::class, ("supplier_id"), ("city_id"))
    )
    val cities: List<CityWithRelationship> = emptyList(),
    @Relation(
        entity = Product::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(ProductsAndSuppliers::class, ("supplier_id"), ("product_id"))
    )
    val products: List<ProductWithRelationship> = emptyList(),
    @Relation(
        entity = Sale::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SupplierAndSales::class, ("supplier_id"), ("sale_id"))
    )
    val sales: List<SaleWithRelationship> = emptyList(),
    @Relation(
        entity = Category::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(SuppliersAndCategories::class, ("supplier_id"), ("category_id"))
    )
    val categories: List<CategoryWithRelationship> = emptyList(),
)
