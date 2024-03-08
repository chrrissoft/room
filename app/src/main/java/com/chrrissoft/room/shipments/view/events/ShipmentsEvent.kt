package com.chrrissoft.room.shipments.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.viewmodels.ShipmentsViewModel.EventHandler
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.shipments.db.objects.Shipping
import com.chrrissoft.room.shipments.db.objects.ShippingWithNestedRelationship

sealed interface ShipmentsEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
            is OnChangePage -> handler.onEvent(event = this)
            is OnSaveRaw -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: Pair<String, ShippingWithRelationship>) : ShipmentsEvent

    data class OnSave(val data: Map<String, ShippingWithNestedRelationship>) : ShipmentsEvent {
        constructor(data: Pair<String, ShippingWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnSaveRaw(val data: Map<String, Shipping>) : ShipmentsEvent {
        constructor(data: Pair<String, Shipping>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, ShippingWithNestedRelationship>) : ShipmentsEvent

    data class OnChange(val data: Pair<String, ShippingWithNestedRelationship>) : ShipmentsEvent

    data class OnDelete(val data: Map<String, ShippingWithRelationship>) : ShipmentsEvent {
        constructor(data: Pair<String, ShippingWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : ShipmentsEvent
}
