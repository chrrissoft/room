package com.chrrissoft.room.categories.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.viewmodels.CategoriesViewModel.EventHandler

sealed interface CategoriesEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : CategoriesEvent

    data class OnSave(val data: Pair<String, CategoryWithRelationship>) : CategoriesEvent

    data class OnCreate(val data: Pair<String, CategoryWithRelationship>) : CategoriesEvent

    data class OnChange(val data: Pair<String, CategoryWithRelationship>) : CategoriesEvent

    data class OnDelete(val data: Map<String, CategoryWithRelationship>) : CategoriesEvent {
        constructor(data: Pair<String, CategoryWithRelationship>) : this(mapOf(data))
    }
}
