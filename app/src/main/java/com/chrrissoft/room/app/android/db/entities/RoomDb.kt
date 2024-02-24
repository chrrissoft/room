package com.chrrissoft.room.app.android.db.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chrrissoft.room.carriers.db.entities.CarrierDao
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.categories.db.entities.CategoryDao
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.cities.db.entities.CityDao
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.common.CategoriesAndOrders
import com.chrrissoft.room.common.CategoriesAndPromotions
import com.chrrissoft.room.common.CategoriesAndSales
import com.chrrissoft.room.common.CitiesAndShipments
import com.chrrissoft.room.common.CitiesAndSuppliers
import com.chrrissoft.room.common.CostumersAndProducts
import com.chrrissoft.room.common.CostumersAndShipments
import com.chrrissoft.room.common.ProductsAndOrders
import com.chrrissoft.room.common.ProductsAndSales
import com.chrrissoft.room.common.ProductsAndShipments
import com.chrrissoft.room.common.ProductsAndSuppliers
import com.chrrissoft.room.common.PromotionsAndOrders
import com.chrrissoft.room.common.SalesAndPromotions
import com.chrrissoft.room.common.SellersAndProducts
import com.chrrissoft.room.common.SellersAndShipments
import com.chrrissoft.room.common.SupplierAndSales
import com.chrrissoft.room.common.SuppliersAndCategories
import com.chrrissoft.room.costumers.db.entities.CostumerDao
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.countries.db.entities.CountryDao
import com.chrrissoft.room.countries.db.objects.Country
import com.chrrissoft.room.orders.db.entities.OrderDao
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.products.db.entities.ProductDao
import com.chrrissoft.room.products.db.objects.Product
import com.chrrissoft.room.promotions.db.entities.PromotionDao
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.sales.db.entities.SaleDao
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sellers.db.entities.SellerDao
import com.chrrissoft.room.sellers.db.objects.Seller
import com.chrrissoft.room.shipments.db.entities.ShippingDao
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.suppliers.db.entities.SupplierDao
import com.chrrissoft.room.suppliers.db.objects.Supplier

@Database(
    entities = [
        Carrier::class,
        Category::class,
        City::class,
        Costumer::class,
        Country::class,
        Order::class,
        Product::class,
        Promotion::class,
        Sale::class,
        Seller::class,
        Shipping::class,
        Supplier::class,

        CategoriesAndOrders::class,
        CategoriesAndPromotions::class,
        CategoriesAndSales::class,
        CitiesAndShipments::class,
        CitiesAndSuppliers::class,
        CostumersAndProducts::class,
        CostumersAndShipments::class,
        ProductsAndOrders::class,
        ProductsAndSales::class,
        ProductsAndShipments::class,
        ProductsAndSuppliers::class,
        PromotionsAndOrders::class,
        SalesAndPromotions::class,
        SellersAndProducts::class,
        SellersAndShipments::class,
        SupplierAndSales::class,
        SuppliersAndCategories::class,
    ],
//    views = [SaleWithRelationship::class],
    version = 3,
)
abstract class RoomDb : RoomDatabase() {
    abstract val supplierDao: SupplierDao
    abstract val shippingDao: ShippingDao
    abstract val sellerDao: SellerDao
    abstract val saleDao: SaleDao
    abstract val promotionDao: PromotionDao
    abstract val productDao: ProductDao
    abstract val orderDao: OrderDao
    abstract val countryDao: CountryDao
    abstract val costumerDao: CostumerDao
    abstract val citiesDao: CityDao
    abstract val carriersDao: CarrierDao
    abstract val categoryDao: CategoryDao
}
