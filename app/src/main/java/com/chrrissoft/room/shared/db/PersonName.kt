package com.chrrissoft.room.shared.db

import androidx.room.ColumnInfo

data class PersonName(
    @ColumnInfo(name = "first_name") val first: String = "",
    @ColumnInfo(name = "middle_name") val middle: String = "",
    @ColumnInfo(name = "surname") val surname: String = "",
)
