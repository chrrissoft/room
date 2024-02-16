package com.chrrissoft.room.categories.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.categories.db.objects.Category
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.db.usecases.DeleteCategoriesUseCase
import com.chrrissoft.room.categories.db.usecases.GetCategoriesUseCase
import com.chrrissoft.room.categories.db.usecases.SaveCategoriesUseCase
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnChange
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnCreate
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnDelete
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnOpen
import com.chrrissoft.room.categories.view.events.CategoriesEvent.OnSave
import com.chrrissoft.room.categories.view.states.CategoriesState
import com.chrrissoft.room.categories.view.viewmodels.CategoriesViewModel.EventHandler
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.app.ResState.Success
import com.chrrissoft.room.ui.entities.SnackbarData
import com.chrrissoft.room.utils.ResStateUtils.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val GetCategoriesUseCase: GetCategoriesUseCase,
    private val SaveCategoriesUseCase: SaveCategoriesUseCase,
    private val DeleteCategoriesUseCase: DeleteCategoriesUseCase,
) : BaseViewModel<EventHandler, CategoriesState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(CategoriesState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadCategories()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadCategory(event.data)

        fun onEvent(event: OnSave) = saveCategories(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(category = Success(event.data))

        fun onEvent(event: OnDelete) = deleteCategories(event.data.mapValues { it.value.category })
    }


    private fun create(data: Pair<String, CategoryWithRelationship>) {
        (state.category as? Success)?.data?.let { saveCategories(mapOf(it)) }
        updateState(category = Success(data))
    }


    private fun saveCategories(data: Map<String, CategoryWithRelationship>) {
        updateState(state.categories.map { it + data })
        saveCategories(data) { updateState() }
    }

    private fun saveCategories(
        data: Map<String, CategoryWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveCategoriesUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteCategories(data: Map<String, Category>) {
        updateState(state.categories.map { it.minus(data.keys) })
        deleteCategories(data) { updateState() }
    }

    private fun deleteCategories(
        data: Map<String, Category>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteCategoriesUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadCategories() = collectCategories { updateState(it) }

    private fun collectCategories(
        block: suspend CoroutineScope.(ResState<Map<String, CategoryWithRelationship>>) -> Unit
    ) = scope.launch { GetCategoriesUseCase().collect { block(it) } }


    private fun loadCategory(id: String) = collectCategory(id) { updateState(category = it) }

    private fun collectCategory(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, CategoryWithRelationship>>) -> Unit
    ) = scope.launch { GetCategoriesUseCase(id).collect { block(it) } }


    private fun updateState(
        categories: ResState<Map<String, CategoryWithRelationship>> = state.categories,
        category: ResState<Pair<String, CategoryWithRelationship>> = state.category,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(category = category, categories = categories, snackbar = snackbar) }
    }
}
