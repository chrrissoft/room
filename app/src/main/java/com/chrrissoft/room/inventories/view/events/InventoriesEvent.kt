package com.chrrissoft.room.inventories.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.inventories.db.objects.InventoryWithRelationship
import com.chrrissoft.room.inventories.view.viewmodels.InventoriesViewModel.EventHandler

sealed interface InventoriesEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : InventoriesEvent

    data class OnSave(val data: Pair<String, InventoryWithRelationship>) : InventoriesEvent

    data class OnCreate(val data: Pair<String, InventoryWithRelationship>) : InventoriesEvent

    data class OnChange(val data: Pair<String, InventoryWithRelationship>) : InventoriesEvent

    data class OnDelete(val data: Map<String, InventoryWithRelationship>) : InventoriesEvent {
        constructor(data: Pair<String, InventoryWithRelationship>) : this(mapOf(data))
    }
}
