package com.chrrissoft.room.payments.db.objects

import androidx.room.Embedded

data class PaymentWithRelationship(
    @Embedded val payment: Payment,
)
