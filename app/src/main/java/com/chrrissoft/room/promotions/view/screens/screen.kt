package com.chrrissoft.room.promotions.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.cross.db.objects.CategoriesAndPromotions
import com.chrrissoft.room.cross.db.objects.SalesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteCategoriesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnDeleteSalesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveCategoriesAndPromotions
import com.chrrissoft.room.cross.view.events.CrossRefEvent.OnSaveSalesAndPromotions
import com.chrrissoft.room.cross.view.state.CrossRefState
import com.chrrissoft.room.products.view.events.ProductsEvent
import com.chrrissoft.room.products.view.states.ProductsState
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionNestedWithRelationship
import com.chrrissoft.room.promotions.view.events.PromotionsEvent
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnChange
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnChangePage
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnCreate
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnDelete
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnOpen
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnSave
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.promotions.view.ui.PromotionList
import com.chrrissoft.room.promotions.view.ui.PromotionWithRelationship
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun PromotionsScreen(
    state: PromotionsState,
    salesState: SalesState,
    productsState: ProductsState,
    categoriesState: CategoriesState,
    crossState: CrossRefState,
    onProductsEvent: (ProductsEvent) -> Unit,
    onCrossEvent: (CrossRefEvent) -> Unit,
    onEvent: (PromotionsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    val id = state.detail.getSuccess()?.first
    val transformCategories = { data: Map<String, CategoryWithRelationship> ->
        data.mapNotNull { id?.let { id -> CategoriesAndPromotions(it.key, id) } }
    }
    val transformSales = { data: Map<String, SaleWithRelationship> ->
        data.mapNotNull { id?.let { id -> SalesAndPromotions(it.key, id) } }
    }

    CommonScreen(
        page = state.page,
        title = "Promotions",
        onChangePage = { onEvent(OnChangePage(it)) },
        onSave = { state.detail.getSuccess()?.let { onEvent(OnSave(it)) } },
        onCreate = { onEvent(OnCreate(it to PromotionNestedWithRelationship(Promotion(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            PromotionWithRelationship(
                state = state.detail,
                onStateChange = { onEvent(OnChange(it)) },
                sales = salesState.listing,
                onRemoveSales = { onCrossEvent(OnDeleteSalesAndPromotions(transformSales(it))) },
                onAddSales = { onCrossEvent(OnSaveSalesAndPromotions(transformSales(it))) },
                products = productsState.listing,
                onRemoveProducts = { data ->
                    data.mapValues { it.value.product.copy(promotionId = null) }
                        .also { onProductsEvent(ProductsEvent.OnSaveRaw(it)) }
                },
                onAddProducts = { data ->
                    data.mapValues { it.value.product.copy(promotionId = id) }
                        .also { onProductsEvent(ProductsEvent.OnSaveRaw(it)) }
                },
                categories = categoriesState.listing,
                onRemoveCategories = {
                    onCrossEvent(OnDeleteCategoriesAndPromotions(transformCategories(it)))
                },
                onAddCategories = {
                    onCrossEvent(OnSaveCategoriesAndPromotions(transformCategories(it)))
                },
            )
        },
        list = {
            PromotionList(
                state = state.listing,
                onDelete = { onEvent(OnDelete(it)) },
                selected = setOf(state.detail.getSuccess()?.first),
                onSelect = { onEvent(OnOpen(it)) },
            )
        },
        snackbarHost = {
            AlarmManagerSnackbar(state = state.snackbar)
            AlarmManagerSnackbar(state = salesState.snackbar)
            AlarmManagerSnackbar(state = productsState.snackbar)
            AlarmManagerSnackbar(state = categoriesState.snackbar)
            AlarmManagerSnackbar(state = crossState.snackbar)
        },
    )
}
