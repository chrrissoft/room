package com.chrrissoft.room.promotions.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.viewmodels.PromotionsViewModel.EventHandler

sealed interface PromotionsEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : PromotionsEvent

    data class OnSave(val data: Pair<String, PromotionWithRelationship>) : PromotionsEvent

    data class OnCreate(val data: Pair<String, PromotionWithRelationship>) : PromotionsEvent

    data class OnChange(val data: Pair<String, PromotionWithRelationship>) : PromotionsEvent

    data class OnDelete(val data: Map<String, PromotionWithRelationship>) : PromotionsEvent {
        constructor(data: Pair<String, PromotionWithRelationship>) : this(mapOf(data))
    }
}
