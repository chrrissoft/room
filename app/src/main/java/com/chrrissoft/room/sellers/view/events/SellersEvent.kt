package com.chrrissoft.room.sellers.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.sellers.db.objects.SellerWithNestedRelationship
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.viewmodels.SellersViewModel.EventHandler
import com.chrrissoft.room.shared.view.Page

sealed interface SellersEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
            is OnChangePage -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: Pair<String, SellerWithRelationship>) : SellersEvent

    data class OnSave(val data: Map<String, SellerWithNestedRelationship>) : SellersEvent {
        constructor(data: Pair<String, SellerWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, SellerWithNestedRelationship>) : SellersEvent

    data class OnChange(val data: Pair<String, SellerWithNestedRelationship>) : SellersEvent

    data class OnDelete(val data: Map<String, SellerWithRelationship>) : SellersEvent {
        constructor(data: Pair<String, SellerWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : SellersEvent
}
