package com.chrrissoft.room.orders.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.viewmodels.OrdersViewModel.EventHandler

sealed interface OrdersEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : OrdersEvent

    data class OnSave(val data: Pair<String, OrderWithRelationship>) : OrdersEvent

    data class OnCreate(val data: Pair<String, OrderWithRelationship>) : OrdersEvent

    data class OnChange(val data: Pair<String, OrderWithRelationship>) : OrdersEvent

    data class OnDelete(val data: Map<String, OrderWithRelationship>) : OrdersEvent {
        constructor(data: Pair<String, OrderWithRelationship>) : this(mapOf(data))
    }
}