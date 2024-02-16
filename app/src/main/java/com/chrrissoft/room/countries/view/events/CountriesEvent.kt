package com.chrrissoft.room.countries.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.countries.db.objects.CountryWithRelationship
import com.chrrissoft.room.countries.view.viewmodels.CountriesViewModel.EventHandler

sealed interface CountriesEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : CountriesEvent

    data class OnSave(val data: Pair<String, CountryWithRelationship>) : CountriesEvent

    data class OnCreate(val data: Pair<String, CountryWithRelationship>) : CountriesEvent

    data class OnChange(val data: Pair<String, CountryWithRelationship>) : CountriesEvent

    data class OnDelete(val data: Map<String, CountryWithRelationship>) : CountriesEvent {
        constructor(data: Pair<String, CountryWithRelationship>) : this(mapOf(data))
    }
}