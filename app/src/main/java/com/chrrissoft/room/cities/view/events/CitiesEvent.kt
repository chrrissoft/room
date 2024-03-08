package com.chrrissoft.room.cities.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.cities.db.objects.CityWithNestedRelationship
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.viewmodels.CitiesViewModel.EventHandler
import com.chrrissoft.room.shared.view.Page

sealed interface CitiesEvent : BaseEvent<EventHandler> {
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

    data class OnOpen(val data: Pair<String, CityWithRelationship>) : CitiesEvent

    data class OnSave(val data: Map<String, CityWithNestedRelationship>) : CitiesEvent {
        constructor(data: Pair<String, CityWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, CityWithNestedRelationship>) : CitiesEvent

    data class OnChange(val data: Pair<String, CityWithNestedRelationship>) : CitiesEvent

    data class OnDelete(val data: Map<String, CityWithRelationship>) : CitiesEvent {
        constructor(data: Pair<String, CityWithRelationship>) : this(mapOf(data))
    }

    class OnChangePage(val data: Page) : CitiesEvent
}
