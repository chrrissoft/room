package com.chrrissoft.room.costumers.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.costumers.db.objects.CostumerWithNestedRelationship
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.viewmodels.CostumersViewModel.EventHandler
import com.chrrissoft.room.shared.view.Page

sealed interface CostumersEvent : BaseEvent<EventHandler> {
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

    data class OnOpen(val data: Pair<String, CostumerWithRelationship>) : CostumersEvent

    data class OnSave(val data: Map<String, CostumerWithNestedRelationship>) : CostumersEvent {
        constructor(data: Pair<String, CostumerWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnSaveRaw(val data: Map<String, Costumer>) : CostumersEvent {
        constructor(data: Pair<String, Costumer>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, CostumerWithNestedRelationship>) : CostumersEvent

    data class OnChange(val data: Pair<String, CostumerWithNestedRelationship>) : CostumersEvent

    data class OnDelete(val data: Map<String, CostumerWithRelationship>) : CostumersEvent {
        constructor(data: Pair<String, CostumerWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : CostumersEvent
}
