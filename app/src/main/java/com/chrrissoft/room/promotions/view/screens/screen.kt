package com.chrrissoft.room.promotions.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.events.PromotionsEvent
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.promotions.view.ui.PromotionList
import com.chrrissoft.room.promotions.view.ui.PromotionWithRelationship
import com.chrrissoft.room.sales.view.states.SalesState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun PromotionsScreen(
    state: PromotionsState,
    salesState: SalesState,
    onEvent: (PromotionsEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Promotions",
        onChangePage = { onEvent(PromotionsEvent.OnChangePage(it)) },
        onSave = { state.promotion.getSuccess()?.let { onEvent(PromotionsEvent.OnSave(it)) } },
        onCreate = { onEvent(PromotionsEvent.OnCreate(it to PromotionWithRelationship(Promotion(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            PromotionWithRelationship(
                state = state.promotion,
                onStateChange = { onEvent(PromotionsEvent.OnChange(it)) },
                sales = salesState.sales,
            )
        },
        list = {
            PromotionList(
                state = state.promotions,
                onDelete = { onEvent(PromotionsEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(PromotionsEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
