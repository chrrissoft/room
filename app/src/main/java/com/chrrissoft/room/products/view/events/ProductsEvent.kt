package com.chrrissoft.room.products.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.products.db.objects.ProductWithNestedRelationship
import com.chrrissoft.room.products.db.objects.ProductWithRelationship
import com.chrrissoft.room.products.view.viewmodels.ProductsViewModel.EventHandler
import com.chrrissoft.room.shared.view.Page

sealed interface ProductsEvent : BaseEvent<EventHandler> {
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

    data class OnOpen(val data: Pair<String, ProductWithRelationship>) : ProductsEvent

    data class OnSave(val data: Map<String, ProductWithNestedRelationship>) : ProductsEvent {
        constructor(data: Pair<String, ProductWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, ProductWithNestedRelationship>) : ProductsEvent

    data class OnChange(val data: Pair<String, ProductWithNestedRelationship>) : ProductsEvent

    data class OnDelete(val data: Map<String, ProductWithRelationship>) : ProductsEvent {
        constructor(data: Pair<String, ProductWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : ProductsEvent
}
