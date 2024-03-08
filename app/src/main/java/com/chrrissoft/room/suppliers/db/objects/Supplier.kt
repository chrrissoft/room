package com.chrrissoft.room.suppliers.db.objects

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chrrissoft.room.common.objects.Direction

@Entity(tableName = "suppliers")
data class Supplier(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String = "",
    @Embedded val direction: Direction = Direction()
)
