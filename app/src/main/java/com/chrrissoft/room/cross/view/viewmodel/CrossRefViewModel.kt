package com.chrrissoft.room.cross.view.viewmodel

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.cross.db.usecases.DeleteCategoriesAndPromotionsUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteCategoriesAndSalesUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteCitiesAndShipmentsUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteCitiesAndSuppliersUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteCostumersAndProductsUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteCostumersAndShipmentsUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteProductsAndSalesUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteProductsAndSuppliersUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteSalesAndPromotionsUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteSellersAndProductsUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteSellersAndShipmentsUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteSupplierAndSalesUseCase
import com.chrrissoft.room.cross.db.usecases.DeleteSuppliersAndCategoriesUseCase
import com.chrrissoft.room.cross.db.usecases.SaveCategoriesAndPromotionsUseCase
import com.chrrissoft.room.cross.db.usecases.SaveCategoriesAndSalesUseCase
import com.chrrissoft.room.cross.db.usecases.SaveCitiesAndShipmentsUseCase
import com.chrrissoft.room.cross.db.usecases.SaveCitiesAndSuppliersUseCase
import com.chrrissoft.room.cross.db.usecases.SaveCostumersAndProductsUseCase
import com.chrrissoft.room.cross.db.usecases.SaveCostumersAndShipmentsUseCase
import com.chrrissoft.room.cross.db.usecases.SaveProductsAndSalesUseCase
import com.chrrissoft.room.cross.db.usecases.SaveProductsAndSuppliersUseCase
import com.chrrissoft.room.cross.db.usecases.SaveSalesAndPromotionsUseCase
import com.chrrissoft.room.cross.db.usecases.SaveSellersAndProductsUseCase
import com.chrrissoft.room.cross.db.usecases.SaveSellersAndShipmentsUseCase
import com.chrrissoft.room.cross.db.usecases.SaveSupplierAndSalesUseCase
import com.chrrissoft.room.cross.db.usecases.SaveSuppliersAndCategoriesUseCase
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCategoriesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCategoriesAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCitiesAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCostumersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteProductsAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSalesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSellersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSuppliersAndCategories
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSuppliersAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCategoriesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCategoriesAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCitiesAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCitiesAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCostumersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCostumersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveProductsAndSales
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveProductsAndSuppliers
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSalesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSellersAndProducts
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSellersAndShipments
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSuppliersAndCategories
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSuppliersAndSales
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.cross.view.viewmodel.CrossRefViewModel.EventHandler
import com.chrrissoft.room.ui.entities.SnackbarData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CrossRefViewModel @Inject constructor(
    private val SaveCategoriesAndPromotionsUseCase: SaveCategoriesAndPromotionsUseCase,
    private val SaveCategoriesAndSalesUseCase: SaveCategoriesAndSalesUseCase,
    private val SaveCitiesAndShipmentsUseCase: SaveCitiesAndShipmentsUseCase,
    private val SaveCitiesAndSuppliersUseCase: SaveCitiesAndSuppliersUseCase,
    private val SaveCostumersAndProductsUseCase: SaveCostumersAndProductsUseCase,
    private val SaveCostumersAndShipmentsUseCase: SaveCostumersAndShipmentsUseCase,
    private val SaveProductsAndSalesUseCase: SaveProductsAndSalesUseCase,
    private val SaveProductsAndSuppliersUseCase: SaveProductsAndSuppliersUseCase,
    private val SaveSalesAndPromotionsUseCase: SaveSalesAndPromotionsUseCase,
    private val SaveSellersAndProductsUseCase: SaveSellersAndProductsUseCase,
    private val SaveSellersAndShipmentsUseCase: SaveSellersAndShipmentsUseCase,
    private val SaveSupplierAndSalesUseCase: SaveSupplierAndSalesUseCase,
    private val SaveSuppliersAndCategoriesUseCase: SaveSuppliersAndCategoriesUseCase,
    private val DeleteCategoriesAndPromotionsUseCase: DeleteCategoriesAndPromotionsUseCase,
    private val DeleteCategoriesAndSalesUseCase: DeleteCategoriesAndSalesUseCase,
    private val DeleteCitiesAndShipmentsUseCase: DeleteCitiesAndShipmentsUseCase,
    private val DeleteCitiesAndSuppliersUseCase: DeleteCitiesAndSuppliersUseCase,
    private val DeleteCostumersAndProductsUseCase: DeleteCostumersAndProductsUseCase,
    private val DeleteCostumersAndShipmentsUseCase: DeleteCostumersAndShipmentsUseCase,
    private val DeleteProductsAndSalesUseCase: DeleteProductsAndSalesUseCase,
    private val DeleteProductsAndSuppliersUseCase: DeleteProductsAndSuppliersUseCase,
    private val DeleteSalesAndPromotionsUseCase: DeleteSalesAndPromotionsUseCase,
    private val DeleteSellersAndProductsUseCase: DeleteSellersAndProductsUseCase,
    private val DeleteSellersAndShipmentsUseCase: DeleteSellersAndShipmentsUseCase,
    private val DeleteSupplierAndSalesUseCase: DeleteSupplierAndSalesUseCase,
    private val DeleteSuppliersAndCategoriesUseCase: DeleteSuppliersAndCategoriesUseCase,
) : BaseViewModel<EventHandler, CrossRefState>() {

    override val eventHandler = EventHandler()

    override val mutableState = MutableStateFlow(CrossRefState())
    override val stateFlow = mutableState.asStateFlow()

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnDeleteCategoriesAndPromotions) =
            launch { DeleteCategoriesAndPromotionsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteCategoriesAndSales) =
            launch { DeleteCategoriesAndSalesUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteCitiesAndShipments) =
            launch { DeleteCitiesAndShipmentsUseCase(event.data).collect { showSnackbar(it) } }

        fun onDeleteCitiesAndSuppliers() =
            launch { DeleteCitiesAndSuppliersUseCase().collect { showSnackbar(it) } }

        fun onDeleteCostumersAndProducts() =
            launch { DeleteCostumersAndProductsUseCase().collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteCostumersAndShipments) =
            launch { DeleteCostumersAndShipmentsUseCase(event.data).collect { showSnackbar(it) } }

        fun onDeleteProductsAndSales() =
            launch { DeleteProductsAndSalesUseCase().collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteProductsAndSuppliers) =
            launch { DeleteProductsAndSuppliersUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteSalesAndPromotions) =
            launch { DeleteSalesAndPromotionsUseCase(event.data).collect { showSnackbar(it) } }

        fun onDeleteSellersAndProducts() =
            launch { DeleteSellersAndProductsUseCase().collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteSellersAndShipments) =
            launch { DeleteSellersAndShipmentsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteSuppliersAndSales) =
            launch { DeleteSupplierAndSalesUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnDeleteSuppliersAndCategories) =
            launch { DeleteSuppliersAndCategoriesUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveCategoriesAndPromotions) =
            launch { SaveCategoriesAndPromotionsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveCategoriesAndSales) =
            launch { SaveCategoriesAndSalesUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveCitiesAndShipments) =
            launch { SaveCitiesAndShipmentsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveCitiesAndSuppliers) =
            launch { SaveCitiesAndSuppliersUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveCostumersAndProducts) =
            launch { SaveCostumersAndProductsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveCostumersAndShipments) =
            launch { SaveCostumersAndShipmentsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveProductsAndSales) =
            launch { SaveProductsAndSalesUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveProductsAndSuppliers) =
            launch { SaveProductsAndSuppliersUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveSalesAndPromotions) =
            launch { SaveSalesAndPromotionsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveSellersAndProducts) =
            launch { SaveSellersAndProductsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveSellersAndShipments) =
            launch { SaveSellersAndShipmentsUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveSuppliersAndSales) =
            launch { SaveSupplierAndSalesUseCase(event.data).collect { showSnackbar(it) } }

        fun onEvent(event: OnSaveSuppliersAndCategories) =
            launch { SaveSuppliersAndCategoriesUseCase(event.data).collect { showSnackbar(it) } }
    }

    override fun updateSnackbarType(messageType: SnackbarData.MessageType) =
        mutableState.update { it.copy(snackbar = state.snackbar.copy(type = messageType)) }
}
