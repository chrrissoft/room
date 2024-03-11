package com.chrrissoft.room.cross.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.cross.view.viewmodel.CrossRefViewModel.EventHandler
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

sealed interface CrossRefEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnDeleteCategoriesAndPromotions -> handler.onEvent(event = this)
            is OnDeleteCategoriesAndSales -> handler.onEvent(event = this)
            is OnDeleteCitiesAndShipments -> handler.onEvent(event = this)
            is OnDeleteCitiesAndSuppliers -> handler.onDeleteCitiesAndSuppliers()
            is OnDeleteCostumersAndProducts -> handler.onDeleteCostumersAndProducts()
            is OnDeleteCostumersAndShipments -> handler.onEvent(event = this)
            is OnDeleteProductsAndSales -> handler.onDeleteProductsAndSales()
            is OnDeleteProductsAndSuppliers -> handler.onEvent(event = this)
            is OnDeleteSalesAndPromotions -> handler.onEvent(event = this)
            is OnDeleteSellersAndProducts -> handler.onDeleteSellersAndProducts()
            is OnDeleteSellersAndShipments -> handler.onEvent(event = this)
            is OnDeleteSuppliersAndSales -> handler.onEvent(event = this)
            is OnDeleteSuppliersAndCategories -> handler.onEvent(event = this)
            is OnSaveCategoriesAndPromotions -> handler.onEvent(event = this)
            is OnSaveCategoriesAndSales -> handler.onEvent(event = this)
            is OnSaveCitiesAndShipments -> handler.onEvent(event = this)
            is OnSaveCitiesAndSuppliers -> handler.onEvent(event = this)
            is OnSaveCostumersAndProducts -> handler.onEvent(event = this)
            is OnSaveCostumersAndShipments -> handler.onEvent(event = this)
            is OnSaveProductsAndSales -> handler.onEvent(event = this)
            is OnSaveProductsAndSuppliers -> handler.onEvent(event = this)
            is OnSaveSalesAndPromotions -> handler.onEvent(event = this)
            is OnSaveSellersAndProducts -> handler.onEvent(event = this)
            is OnSaveSellersAndShipments -> handler.onEvent(event = this)
            is OnSaveSuppliersAndSales -> handler.onEvent(event = this)
            is OnSaveSuppliersAndCategories -> handler.onEvent(event = this)
        }
    }

    data class OnSaveCategoriesAndPromotions(val data: List<CategoriesAndPromotions>) :
        CrossRefEvent

    data class OnSaveCategoriesAndSales(val data: List<CategoriesAndSales>) : CrossRefEvent

    data class OnSaveCitiesAndShipments(val data: List<CitiesAndShipments>) : CrossRefEvent

    data class OnSaveCitiesAndSuppliers(val data: List<CitiesAndSuppliers>) : CrossRefEvent

    data class OnSaveCostumersAndProducts(val data: List<CostumersAndProducts>) : CrossRefEvent

    data class OnSaveCostumersAndShipments(val data: List<CostumersAndShipments>) : CrossRefEvent

    data class OnSaveProductsAndSales(val data: List<ProductsAndSales>) : CrossRefEvent

    data class OnSaveProductsAndSuppliers(val data: List<ProductsAndSuppliers>) : CrossRefEvent

    data class OnSaveSalesAndPromotions(val data: List<SalesAndPromotions>) : CrossRefEvent

    data class OnSaveSellersAndProducts(val data: List<SellersAndProducts>) : CrossRefEvent

    data class OnSaveSellersAndShipments(val data: List<SellersAndShipments>) : CrossRefEvent

    data class OnSaveSuppliersAndSales(val data: List<SuppliersAndSales>) : CrossRefEvent

    data class OnSaveSuppliersAndCategories(val data: List<SuppliersAndCategories>) :
        CrossRefEvent


    data class OnDeleteCategoriesAndPromotions(val data: List<CategoriesAndPromotions>) :
        CrossRefEvent

    data class OnDeleteCategoriesAndSales(val data: List<CategoriesAndSales>) : CrossRefEvent

    data class OnDeleteCitiesAndShipments(val data: List<CitiesAndShipments>) : CrossRefEvent

    object OnDeleteCitiesAndSuppliers : CrossRefEvent

    object OnDeleteCostumersAndProducts : CrossRefEvent

    data class OnDeleteCostumersAndShipments(val data: List<CostumersAndShipments>) :
        CrossRefEvent


    object OnDeleteProductsAndSales : CrossRefEvent

    data class OnDeleteProductsAndSuppliers(val data: List<ProductsAndSuppliers>) : CrossRefEvent

    data class OnDeleteSalesAndPromotions(val data: List<SalesAndPromotions>) : CrossRefEvent

    object OnDeleteSellersAndProducts : CrossRefEvent

    data class OnDeleteSellersAndShipments(val data: List<SellersAndShipments>) : CrossRefEvent

    data class OnDeleteSuppliersAndSales(val data: List<SuppliersAndSales>) : CrossRefEvent

    data class OnDeleteSuppliersAndCategories(val data: List<SuppliersAndCategories>) :
        CrossRefEvent
}
