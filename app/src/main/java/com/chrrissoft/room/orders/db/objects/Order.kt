package com.chrrissoft.room.orders.db.objects

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.common.objects.Direction
import com.chrrissoft.room.sales.db.objects.Sale
import com.chrrissoft.room.shipments.db.objects.Shipping

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = Sale::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.SET_DEFAULT,
        ),
    ]
)
data class Order(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @Embedded val direction: Direction = Direction(),
    @ColumnInfo(name = "city_id") val cityId: String = "",
    @ColumnInfo(name = "shipping_id", defaultValue = "NULL") val shippingId: String? = "",
) {
    companion object {
        val invalid = Order("")
    }
}
