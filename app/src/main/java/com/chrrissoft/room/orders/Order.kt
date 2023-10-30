package com.chrrissoft.room.orders

import androidx.room.*
import com.chrrissoft.room.cities.City
import com.chrrissoft.room.common.Direction
import com.chrrissoft.room.costumers.Costumer
import com.chrrissoft.room.sellers.Seller
import com.chrrissoft.room.shipping.Shipping

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["city_id"]),
        ForeignKey(
            entity = Seller::class,
            parentColumns = ["id"],
            childColumns = ["seller_id"]),
        ForeignKey(
            entity = Costumer::class,
            parentColumns = ["id"],
            childColumns = ["costumer_id"]),
        ForeignKey(
            entity = Shipping::class,
            parentColumns = ["id"],
            childColumns = ["shipping_id"]),
    ])
data class Order(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @Embedded
    @ColumnInfo(name = "direction") val direction: Direction,
    @ColumnInfo(name = "city_id") val cityId: Long,
    @ColumnInfo(name = "seller_id") val sellerId: Long,
    @ColumnInfo(name = "costumer_id") val costumerId: Long,
    @ColumnInfo(name = "shipping_id") val shippingId: Long,
)
