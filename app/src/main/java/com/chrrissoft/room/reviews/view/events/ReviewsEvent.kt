package com.chrrissoft.room.reviews.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.reviews.db.objects.ReviewWithRelationship
import com.chrrissoft.room.reviews.view.viewmodels.ReviewsViewModel.EventHandler

sealed interface ReviewsEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : ReviewsEvent

    data class OnSave(val data: Pair<String, ReviewWithRelationship>) : ReviewsEvent

    data class OnCreate(val data: Pair<String, ReviewWithRelationship>) : ReviewsEvent

    data class OnChange(val data: Pair<String, ReviewWithRelationship>) : ReviewsEvent

    data class OnDelete(val data: Map<String, ReviewWithRelationship>) : ReviewsEvent {
        constructor(data: Pair<String, ReviewWithRelationship>) : this(mapOf(data))
    }
}
