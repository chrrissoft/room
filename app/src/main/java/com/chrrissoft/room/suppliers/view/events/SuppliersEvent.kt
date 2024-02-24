package com.chrrissoft.room.suppliers.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship
import com.chrrissoft.room.suppliers.view.viewmodels.SuppliersViewModel.EventHandler

sealed interface SuppliersEvent : BaseEvent<EventHandler> {
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

    data class OnOpen(val data: String) : SuppliersEvent

    data class OnSave(val data: Pair<String, SupplierWithRelationship>) : SuppliersEvent

    data class OnCreate(val data: Pair<String, SupplierWithRelationship>) : SuppliersEvent

    data class OnChange(val data: Pair<String, SupplierWithRelationship>) : SuppliersEvent

    data class OnDelete(val data: Map<String, SupplierWithRelationship>) : SuppliersEvent {
        constructor(data: Pair<String, SupplierWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : SuppliersEvent
}
