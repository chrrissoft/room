package com.chrrissoft.room.reviews.view.viewmodels

import com.chrrissoft.room.base.view.handler.BaseEventHandler
import com.chrrissoft.room.base.view.viewmodel.BaseViewModel
import com.chrrissoft.room.reviews.db.objects.Review
import com.chrrissoft.room.reviews.db.objects.ReviewWithRelationship
import com.chrrissoft.room.reviews.db.usecases.DeleteReviewsUseCase
import com.chrrissoft.room.reviews.db.usecases.GetReviewsUseCase
import com.chrrissoft.room.reviews.db.usecases.SaveReviewsUseCase
import com.chrrissoft.room.reviews.view.events.ReviewsEvent.OnChange
import com.chrrissoft.room.reviews.view.events.ReviewsEvent.OnCreate
import com.chrrissoft.room.reviews.view.events.ReviewsEvent.OnDelete
import com.chrrissoft.room.reviews.view.events.ReviewsEvent.OnOpen
import com.chrrissoft.room.reviews.view.events.ReviewsEvent.OnSave
import com.chrrissoft.room.reviews.view.states.ReviewsState
import com.chrrissoft.room.reviews.view.viewmodels.ReviewsViewModel.EventHandler
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
class ReviewsViewModel @Inject constructor(
    private val GetReviewsUseCase: GetReviewsUseCase,
    private val SaveReviewsUseCase: SaveReviewsUseCase,
    private val DeleteReviewsUseCase: DeleteReviewsUseCase,
) : BaseViewModel<EventHandler, ReviewsState>() {
    override val eventHandler = EventHandler()
    override val _state = MutableStateFlow(ReviewsState())
    override val stateFlow = _state.asStateFlow()

    init {
        loadReviews()
    }

    inner class EventHandler : BaseEventHandler() {
        fun onEvent(event: OnOpen) = loadReview(event.data)

        fun onEvent(event: OnSave) = saveReviews(mapOf(event.data))

        fun onEvent(event: OnCreate) = create(event.data)

        fun onEvent(event: OnChange) = updateState(review = Success(event.data))

        fun onEvent(event: OnDelete) = deleteReviews(event.data.mapValues { it.value.review })
    }


    private fun create(data: Pair<String, ReviewWithRelationship>) {
        (state.review as? Success)?.data?.let { saveReviews(mapOf(it)) }
        updateState(review = Success(data))
    }


    private fun saveReviews(data: Map<String, ReviewWithRelationship>) {
        updateState(state.reviews.map { it + data })
        saveReviews(data) { updateState() }
    }

    private fun saveReviews(
        data: Map<String, ReviewWithRelationship>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { SaveReviewsUseCase(data.map { it.value }).collect { block(it) } }


    private fun deleteReviews(data: Map<String, Review>) {
        updateState(state.reviews.map { it.minus(data.keys) })
        deleteReviews(data) { updateState() }
    }

    private fun deleteReviews(
        data: Map<String, Review>,
        block: suspend CoroutineScope.(ResState<Any>) -> Unit
    ) = scope.launch { DeleteReviewsUseCase(data.map { it.value }).collect { block(it) } }


    private fun loadReviews() = collectReviews { updateState(it) }

    private fun collectReviews(
        block: suspend CoroutineScope.(ResState<Map<String, ReviewWithRelationship>>) -> Unit
    ) = scope.launch { GetReviewsUseCase().collect { block(it) } }


    private fun loadReview(id: String) = collectReview(id) { updateState(review = it) }

    private fun collectReview(
        id: String,
        block: suspend CoroutineScope.(ResState<Pair<String, ReviewWithRelationship>>) -> Unit
    ) = scope.launch { GetReviewsUseCase(id).collect { block(it) } }


    private fun updateState(
        reviews: ResState<Map<String, ReviewWithRelationship>> = state.reviews,
        review: ResState<Pair<String, ReviewWithRelationship>> = state.review,
        snackbar: SnackbarData = state.snackbar,
    ) {
        _state.update { it.copy(review = review, reviews = reviews, snackbar = snackbar) }
    }
}
