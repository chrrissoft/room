package com.chrrissoft.room.shared.db

import androidx.room.ColumnInfo

data class PersonName(
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "middle_name") val middleName: String,
    @ColumnInfo(name = "surname") val surname: String,
)
