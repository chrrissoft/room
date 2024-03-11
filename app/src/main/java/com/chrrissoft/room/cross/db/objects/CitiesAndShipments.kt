package com.chrrissoft.room.cross.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.shipments.db.objects.Shipping

@Entity(
    tableName = "cities_and_shipments",
    primaryKeys = ["city_id", "shipping_id"],
    foreignKeys = [
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["city_id"]),
        ForeignKey(
            // onUpdate = ForeignKey.CASCADE,
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"]),
    ]
)
data class CitiesAndShipments(
    @ColumnInfo(name = "city_id") val cityId: String,
    @ColumnInfo(name = "shipping_id") val shippingId: String,
)
