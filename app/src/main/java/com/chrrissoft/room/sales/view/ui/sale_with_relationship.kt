package com.chrrissoft.room.sales.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.costumers.db.objects.CostumerWithRelationship
import com.chrrissoft.room.costumers.view.ui.CostumerListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.orders.view.ui.OrderListSheet
import com.chrrissoft.room.payments.db.objects.PaymentWithRelationship
import com.chrrissoft.room.payments.view.ui.PaymentListSheet
import com.chrrissoft.room.promotions.db.objects.PromotionWithRelationship
import com.chrrissoft.room.promotions.view.ui.PromotionListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.sellers.view.ui.SellerListSheet
import com.chrrissoft.room.shipments.db.objects.ShippingWithRelationship
import com.chrrissoft.room.shipments.view.ui.ShippingListSheet
import com.chrrissoft.room.ui.components.SelectableRoomTextField

@Composable
fun SaleWithRelationship(
    state: SaleWithRelationship,
    onStateChange: (SaleWithRelationship) -> Unit,
    sellers: List<SellerWithRelationship>,
    promotions: List<PromotionWithRelationship>,
    costumers: List<CostumerWithRelationship>,
    shipments: List<ShippingWithRelationship>,
    orders: List<OrderWithRelationship>,
    payments: List<PaymentWithRelationship>,
    modifier: Modifier = Modifier,
) {
    var showOrders by remember { mutableStateOf(value = false) }

    if (showOrders) {
        OrderListSheet(
            state = orders,
            onSelect = { onStateChange(state.copy(order = it.order)) },
            selected = setOf(state.order.id),
            onDismissRequest = { showOrders = false })
    }


    var showSellers by remember { mutableStateOf(value = false) }

    if (showSellers) {
        SellerListSheet(
            state = sellers,
            selected = setOf(state.seller.id),
            onSelect = { onStateChange(state.copy(seller = it.seller)) },
            onDismissRequest = { showSellers = false })
    }


    var showPromotions by remember { mutableStateOf(value = false) }

    if (showPromotions) {
        PromotionListSheet(
            state = promotions,
            selected = setOf(state.promotion.id),
            onSelect = { onStateChange(state.copy(promotion = it.promotion)) },
            onDismissRequest = { showPromotions = false })
    }


    var showCostumers by remember { mutableStateOf(value = false) }

    if (showCostumers) {
        CostumerListSheet(
            state = costumers,
            selected = setOf(state.costumer.id),
            onSelect = { onStateChange(state.copy(costumer = it.costumer)) },
            onDismissRequest = { showCostumers = false })
    }


    var showPayments by remember { mutableStateOf(value = false) }

    if (showPayments) {
        PaymentListSheet(
            state = payments,
            selected = setOf(state.payment.id),
            onSelect = { onStateChange(state.copy(payment = it.payment)) },
            onDismissRequest = { showPayments = false })
    }


    var showShipments by remember { mutableStateOf(value = false) }

    if (showShipments) {
        ShippingListSheet(
            state = shipments,
            selected = setOf(state.shipping.id),
            onSelect = { onStateChange(state.copy(shipping = it.shipping)) },
            onDismissRequest = { showShipments = false })
    }


    Column(modifier) {
        Sale(state = state.sale, onStateChange = { onStateChange(state.copy(sale = it)) })
        SelectableRoomTextField(value = state.seller.name.firstName) {  }
        SelectableRoomTextField(value = state.promotion.name) {  }
        SelectableRoomTextField(value = state.costumer.name.firstName) {  }
        SelectableRoomTextField(value = state.shipping.state.name) {  }
        SelectableRoomTextField(value = state.order.direction.street) {  }
        SelectableRoomTextField(value = state.payment.name) {  }
    }
}
