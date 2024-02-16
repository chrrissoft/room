package com.chrrissoft.room.sales.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.payments.db.objects.Payment
import com.chrrissoft.room.sellers.db.objects.Seller

data class SaleWithRelationship(
    @Embedded val sale: Sale,
    @Relation(parentColumn = "seller_id", entityColumn = "id")
    val seller: Seller,
    @Relation(parentColumn = "costumer_id", entityColumn = "id")
    val costumer: Costumer,
    @Relation(parentColumn = "order_id", entityColumn = "id")
    val order: Order,
    @Relation(parentColumn = "payment_method_id", entityColumn = "id")
    val payment: Payment,
)
