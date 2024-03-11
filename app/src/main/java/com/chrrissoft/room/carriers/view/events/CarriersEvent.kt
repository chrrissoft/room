package com.chrrissoft.room.carriers.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.carriers.db.objects.Carrier
import com.chrrissoft.room.carriers.db.objects.CarrierWithNestedRelationship
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.view.viewmodels.CarriersViewModel.EventHandler
import com.chrrissoft.room.shared.view.Page

sealed interface CarriersEvent : BaseEvent<EventHandler> {
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

    data class OnOpen(val data: Pair<String, CarrierWithRelationship>) : CarriersEvent

    data class OnSave(val data: Map<String, CarrierWithNestedRelationship>) : CarriersEvent {
        constructor(data: Pair<String, CarrierWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnSaveRaw(val data: Map<String, Carrier>) : CarriersEvent {
        constructor(data: Pair<String, Carrier>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, CarrierWithNestedRelationship>) : CarriersEvent

    data class OnChange(val data: Pair<String, CarrierWithNestedRelationship>) : CarriersEvent

    data class OnDelete(val data: Map<String, CarrierWithRelationship>) : CarriersEvent {
        constructor(data: Pair<String, CarrierWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : CarriersEvent
}
