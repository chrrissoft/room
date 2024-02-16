package com.chrrissoft.room.carriers.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.carriers.db.objects.CarrierWithRelationship
import com.chrrissoft.room.carriers.view.viewmodels.CarriersViewModel.EventHandler

sealed interface CarriersEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : CarriersEvent

    data class OnSave(val data: Pair<String, CarrierWithRelationship>) : CarriersEvent

    data class OnCreate(val data: Pair<String, CarrierWithRelationship>) : CarriersEvent

    data class OnChange(val data: Pair<String, CarrierWithRelationship>) : CarriersEvent

    data class OnDelete(val data: Map<String, CarrierWithRelationship>) : CarriersEvent {
        constructor(data: Pair<String, CarrierWithRelationship>) : this(mapOf(data))
    }
}
