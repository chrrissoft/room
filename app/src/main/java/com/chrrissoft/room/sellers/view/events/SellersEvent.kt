package com.chrrissoft.room.sellers.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.viewmodels.SellersViewModel.EventHandler

sealed interface SellersEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : SellersEvent

    data class OnSave(val data: Pair<String, SellerWithRelationship>) : SellersEvent

    data class OnCreate(val data: Pair<String, SellerWithRelationship>) : SellersEvent

    data class OnChange(val data: Pair<String, SellerWithRelationship>) : SellersEvent

    data class OnDelete(val data: Map<String, SellerWithRelationship>) : SellersEvent {
        constructor(data: Pair<String, SellerWithRelationship>) : this(mapOf(data))
    }
}