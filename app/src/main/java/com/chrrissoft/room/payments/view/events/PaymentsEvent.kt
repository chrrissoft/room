package com.chrrissoft.room.payments.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship
import com.chrrissoft.room.payments.view.viewmodels.PaymentsViewModel.EventHandler

sealed interface PaymentsEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: String) : PaymentsEvent

    data class OnSave(val data: Pair<String, PaymentWithRelationship>) : PaymentsEvent

    data class OnCreate(val data: Pair<String, PaymentWithRelationship>) : PaymentsEvent

    data class OnChange(val data: Pair<String, PaymentWithRelationship>) : PaymentsEvent

    data class OnDelete(val data: Map<String, PaymentWithRelationship>) : PaymentsEvent {
        constructor(data: Pair<String, PaymentWithRelationship>) : this(mapOf(data))
    }
}
