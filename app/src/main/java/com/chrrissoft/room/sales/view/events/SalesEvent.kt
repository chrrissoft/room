package com.chrrissoft.room.sales.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.sales.db.objects.SaleWithNestedRelationship
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.viewmodels.SalesViewModel.EventHandler
import com.chrrissoft.room.shared.view.Page

sealed interface SalesEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnSaveRaw -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
            is OnChangePage -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: Pair<String, SaleWithRelationship>) : SalesEvent

    data class OnSave(val data: Map<String, SaleWithNestedRelationship>) : SalesEvent {
        constructor(data: Pair<String, SaleWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnSaveRaw(val data: Map<String, Sale>) : SalesEvent {
        constructor(data: Pair<String, Sale>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, SaleWithNestedRelationship>) : SalesEvent

    data class OnChange(val data: Pair<String, SaleWithNestedRelationship>) : SalesEvent

    data class OnDelete(val data: Map<String, SaleWithRelationship>) : SalesEvent {
        constructor(data: Pair<String, SaleWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : SalesEvent
}
