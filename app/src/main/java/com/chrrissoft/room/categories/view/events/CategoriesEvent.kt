package com.chrrissoft.room.categories.view.events

import com.chrrissoft.room.base.view.event.BaseEvent
import com.chrrissoft.room.categories.db.objects.CategoryWithNestedRelationship
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.categories.view.viewmodels.CategoriesViewModel.EventHandler
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.shared.view.Page
import com.chrrissoft.room.suppliers.db.objects.SupplierWithRelationship

sealed interface CategoriesEvent : BaseEvent<EventHandler> {
    override fun resolve(handler: EventHandler) {
        when (this) {
            is OnOpen -> handler.onEvent(event = this)
            is OnSave -> handler.onEvent(event = this)
            is OnCreate -> handler.onEvent(event = this)
            is OnChange -> handler.onEvent(event = this)
            is OnDelete -> handler.onEvent(event = this)
            is OnChangePage -> handler.onEvent(event = this)
            is OnAddPromotions -> handler.onEvent(event = this)
            is OnRemovePromotions -> handler.onEvent(event = this)
            is OnAddOrders -> handler.onEvent(event = this)
            is OnAddSales -> handler.onEvent(event = this)
            is OnAddSuppliers -> handler.onEvent(event = this)
            is OnRemoveOrders -> handler.onEvent(event = this)
            is OnRemoveSales -> handler.onEvent(event = this)
            is OnRemoveSuppliers -> handler.onEvent(event = this)
        }
    }

    data class OnOpen(val data: Pair<String, CategoryWithRelationship>) : CategoriesEvent

    data class OnSave(val data: Map<String, CategoryWithNestedRelationship>) : CategoriesEvent {
        constructor(data: Pair<String, CategoryWithNestedRelationship>) : this(mapOf(data))
    }

    data class OnCreate(val data: Pair<String, CategoryWithNestedRelationship>) : CategoriesEvent

    data class OnChange(val data: Pair<String, CategoryWithNestedRelationship>) : CategoriesEvent

    data class OnDelete(val data: Map<String, CategoryWithRelationship>) : CategoriesEvent {
        constructor(data: Pair<String, CategoryWithRelationship>) : this(mapOf(data))
    }

    data class OnAddPromotions(val data: List<PromotionWithRelationship>) : CategoriesEvent {
        constructor(vararg data: PromotionWithRelationship) : this(data.toList())
    }

    data class OnRemovePromotions(val data: List<PromotionWithRelationship>) : CategoriesEvent {
        constructor(vararg data: PromotionWithRelationship) : this(data.toList())
    }

    data class OnAddSales(val data: List<SaleWithRelationship>) : CategoriesEvent {
        constructor(vararg data: SaleWithRelationship) : this(data.toList())
    }

    data class OnRemoveSales(val data: List<SaleWithRelationship>) : CategoriesEvent {
        constructor(vararg data: SaleWithRelationship) : this(data.toList())
    }

    data class OnAddOrders(val data: List<OrderWithRelationship>) : CategoriesEvent {
        constructor(vararg data: OrderWithRelationship) : this(data.toList())
    }

    data class OnRemoveOrders(val data: List<OrderWithRelationship>) : CategoriesEvent {
        constructor(vararg data: OrderWithRelationship) : this(data.toList())
    }

    data class OnAddSuppliers(val data: List<SupplierWithRelationship>) : CategoriesEvent {
        constructor(vararg data: SupplierWithRelationship) : this(data.toList())
    }

    data class OnRemoveSuppliers(val data: List<SupplierWithRelationship>) : CategoriesEvent {
        constructor(vararg data: SupplierWithRelationship) : this(data.toList())
    }

    class OnChangePage(val data: Page) : CategoriesEvent
}
