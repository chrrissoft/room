package com.chrrissoft.room.shipments.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.viewmodels.ShipmentsViewModel.EventHandler

sealed interface ShipmentsEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : ShipmentsEvent

    data class OnSave(val data: Pair<String, ShippingWithRelationship>) : ShipmentsEvent

    data class OnCreate(val data: Pair<String, ShippingWithRelationship>) : ShipmentsEvent

    data class OnChange(val data: Pair<String, ShippingWithRelationship>) : ShipmentsEvent

    data class OnDelete(val data: Map<String, ShippingWithRelationship>) : ShipmentsEvent {
        constructor(data: Pair<String, ShippingWithRelationship>) : this(mapOf(data))
    }
}