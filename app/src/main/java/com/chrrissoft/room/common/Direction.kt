package com.chrrissoft.room.common

import androidx.room.ColumnInfo

data class Direction(
    @ColumnInfo(name = "street") val street: String = "",
    @ColumnInfo(name = "postal_code") val postalCode: String = "",
)

