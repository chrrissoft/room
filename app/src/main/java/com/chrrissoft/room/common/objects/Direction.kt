package com.chrrissoft.room.common.objects

import androidx.room.ColumnInfo

data class Direction(
    @ColumnInfo(name = "street") val street: String = "",
    @ColumnInfo(name = "postal_code") val postalCode: String = "",
)

