package com.chrrissoft.room.app.android.db.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chrrissoft.room.carriers.db.entities.CarrierDao
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.categories.db.entities.CategoryDao
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.cities.db.entities.CityDao
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.cross.db.entities.CategoriesAndPromotionsDao
import com.chrrissoft.room.cross.db.entities.CategoriesAndSalesDao
import com.chrrissoft.room.cross.db.entities.CitiesAndShipmentsDao
import com.chrrissoft.room.cross.db.entities.CitiesAndSuppliersDao
import com.chrrissoft.room.cross.db.entities.CostumersAndProductsDao
import com.chrrissoft.room.cross.db.entities.CostumersAndShipmentsDao
import com.chrrissoft.room.cross.db.entities.ProductsAndSalesDao
import com.chrrissoft.room.cross.db.entities.ProductsAndSuppliersDao
import com.chrrissoft.room.cross.db.entities.SalesAndPromotionsDao
import com.chrrissoft.room.cross.db.entities.SellersAndProductsDao
import com.chrrissoft.room.cross.db.entities.SellersAndShipmentsDao
import com.chrrissoft.room.cross.db.entities.SupplierAndSalesDao
import com.chrrissoft.room.cross.db.entities.SuppliersAndCategoriesDao
import com.chrrissoft.room.cross.db.objects.CategoriesAndPromotions
import com.chrrissoft.room.cross.db.objects.CategoriesAndSales
import com.chrrissoft.room.cross.db.objects.CitiesAndShipments
import com.chrrissoft.room.cross.db.objects.CitiesAndSuppliers
import com.chrrissoft.room.cross.db.objects.CostumersAndProducts
import com.chrrissoft.room.cross.db.objects.CostumersAndShipments
import com.chrrissoft.room.cross.db.objects.ProductsAndSales
import com.chrrissoft.room.cross.db.objects.ProductsAndSuppliers
import com.chrrissoft.room.cross.db.objects.SalesAndPromotions
import com.chrrissoft.room.cross.db.objects.SellersAndProducts
import com.chrrissoft.room.cross.db.objects.SellersAndShipments
import com.chrrissoft.room.cross.db.objects.SuppliersAndSales
import com.chrrissoft.room.cross.db.objects.SuppliersAndCategories
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

        CategoriesAndPromotions::class,
        CategoriesAndSales::class,
        CitiesAndShipments::class,
        CitiesAndSuppliers::class,
        CostumersAndProducts::class,
        CostumersAndShipments::class,
        ProductsAndSales::class,
        ProductsAndSuppliers::class,
        SalesAndPromotions::class,
        SellersAndProducts::class,
        SellersAndShipments::class,
        SuppliersAndSales::class,
        SuppliersAndCategories::class,
    ],
    version = 1,
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

    abstract val categoriesAndPromotionsDao: CategoriesAndPromotionsDao
    abstract val categoriesAndSalesDao: CategoriesAndSalesDao
    abstract val citiesAndShipmentsDao: CitiesAndShipmentsDao
    abstract val citiesAndSuppliersDao: CitiesAndSuppliersDao
    abstract val costumersAndProductsDao: CostumersAndProductsDao
    abstract val costumersAndShipmentsDao: CostumersAndShipmentsDao
    abstract val productsAndSalesDao: ProductsAndSalesDao
    abstract val productsAndSuppliersDao: ProductsAndSuppliersDao
    abstract val salesAndPromotionsDao: SalesAndPromotionsDao
    abstract val sellersAndProductsDao: SellersAndProductsDao
    abstract val sellersAndShipmentsDao: SellersAndShipmentsDao
    abstract val supplierAndSalesDao: SupplierAndSalesDao
    abstract val suppliersAndCategoriesDao: SuppliersAndCategoriesDao
}
