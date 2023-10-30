package com.chrrissoft.room.common

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.cities.City
import com.chrrissoft.room.suppliers.Supplier

@Entity(
    tableName = "cities_and_suppliers",
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["city_id"]),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            entity = Supplier::class,
            parentColumns = ["id"],
            childColumns = ["supplier_id"]),
    ]
)
data class CitiesAndSuppliers(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "city_id") val cityId: Long,
    @ColumnInfo(name = "supplier_id") val supplierId: Long,
)
