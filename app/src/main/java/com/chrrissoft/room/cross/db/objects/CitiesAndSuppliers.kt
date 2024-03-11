package com.chrrissoft.room.cross.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.suppliers.db.objects.Supplier

@Entity(
    tableName = "cities_and_suppliers",
    foreignKeys = [
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["city_id"]),
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Supplier::class,
            parentColumns = ["id"],
            childColumns = ["supplier_id"]),
    ]
)
data class CitiesAndSuppliers(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "city_id") val cityId: String,
    @ColumnInfo(name = "supplier_id") val supplierId: String,
)
