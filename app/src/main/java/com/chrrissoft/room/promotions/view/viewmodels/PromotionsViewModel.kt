package com.chrrissoft.room.promotions.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.promotions.db.objects.Promotion
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.db.usecases.DeletePromotionsUseCase
import com.chrrissoft.room.promotions.db.usecases.GetPromotionsUseCase
import com.chrrissoft.room.promotions.db.usecases.SavePromotionsUseCase
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnChange
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnCreate
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnDelete
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnOpen
import com.chrrissoft.room.promotions.view.events.PromotionsEvent.OnSave
import com.chrrissoft.room.promotions.view.states.PromotionsState
import com.chrrissoft.room.promotions.view.viewmodels.PromotionsViewModel.EventHandler
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
class PromotionsViewModel @Inject constructor(
    private val GetPromotionsUseCase: GetPromotionsUseCase,
    private val SavePromotionsUseCase: SavePromotionsUseCase,
    private val DeletePromotionsUseCase: DeletePromotionsUseCase,
) : BaseViewModel<EventHandler, PromotionsState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(PromotionsState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadPromotions()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadPromotion(event.data)

        fun onEvent(event: OnSave) = savePromotions(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(promotion = Success(event.data))

        fun onEvent(event: OnDelete) = deletePromotions(event.data.mapValues { it.value.promotion })
    }


    private fun create(data: Pair<String, PromotionWithRelationship>) {
        (state.promotion as? Success)?.data?.let { savePromotions(mapOf(it)) }
        updateState(promotion = Success(data))
    }


    private fun savePromotions(data: Map<String, PromotionWithRelationship>) {
        updateState(state.promotions.map { it + data })
        savePromotions(data) { updateState() }
    }

    private fun savePromotions(
        data: Map<String, PromotionWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SavePromotionsUseCase(data.map { it.value }).collect { block(it) } }


    private fun deletePromotions(data: Map<String, Promotion>) {
        updateState(state.promotions.map { it.minus(data.keys) })
        deletePromotions(data) { updateState() }
    }

    private fun deletePromotions(
        data: Map<String, Promotion>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeletePromotionsUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadPromotions() = collectPromotions { updateState(it) }

    private fun collectPromotions(
        block: suspend CoroutineScope.(ResState<Map<String, PromotionWithRelationship>>) -> Unit
    ) = scope.launch { GetPromotionsUseCase().collect { block(it) } }


    private fun loadPromotion(id: String) = collectPromotion(id) { updateState(promotion = it) }

    private fun collectPromotion(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, PromotionWithRelationship>>) -> Unit
    ) = scope.launch { GetPromotionsUseCase(id).collect { block(it) } }


    private fun updateState(
        promotions: ResState<Map<String, PromotionWithRelationship>> = state.promotions,
        promotion: ResState<Pair<String, PromotionWithRelationship>> = state.promotion,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(promotion = promotion, promotions = promotions, snackbar = snackbar) }
    }
}
