package com.chrrissoft.room.payments.view.ui

enum class Payment {
    CASH
    ;

    companion object {
        val payments: List<Payment> = listOf(CASH)
    }

}