package com.chrrissoft.room.categories.view.screens

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.view.events.CategoriesEvent
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.ui.CategoriesList
import com.chrrissoft.room.categories.view.ui.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.AlarmManagerSnackbar
import com.chrrissoft.room.ui.components.CommonScreen
import com.chrrissoft.room.utils.ResStateUtils.getSuccess

@Composable
fun CategoriesScreen(
    state: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit,
    onOpenDrawer: () -> Unit,
) {
    CommonScreen(
        page = state.page,
        title = "Categories",
        onChangePage = { onEvent(CategoriesEvent.OnChangePage(it)) },
        onSave = { state.category.getSuccess()?.let { onEvent(CategoriesEvent.OnSave(it)) } },
        onCreate = { onEvent(CategoriesEvent.OnCreate(it to CategoryWithRelationship(Category(it)))) },
        onNavigation = onOpenDrawer,
        details = {
            CategoryWithRelationship(
                state = state.category,
                onStateChange = { onEvent(CategoriesEvent.OnChange(it)) },
                orders = ResState.None,
                promotions = ResState.None,
                sales = ResState.None,
            )
        },
        list = {
            CategoriesList(
                state = state.categories,
                onDelete = { onEvent(CategoriesEvent.OnDelete(it)) },
                selected = setOf(),
                onSelect = { onEvent(CategoriesEvent.OnOpen(it.first)) },
            )
        },
        snackbarHost = { AlarmManagerSnackbar(state = state.snackbar) }
    )
}
