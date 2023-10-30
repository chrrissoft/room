package com.chrrissoft.room.sales

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.promotions.Promotion
import com.chrrissoft.room.shipping.Shipping
import com.chrrissoft.room.costumers.Costumer
import com.chrrissoft.room.orders.Order
import com.chrrissoft.room.payments.PaymentMethod
import com.chrrissoft.room.sellers.Seller


@Entity(tableName = "sales", foreignKeys = [
    ForeignKey(
        entity = Shipping::class,
        parentColumns = ["id"],
        childColumns = ["shipping_id"]),
    ForeignKey(
        entity = Order::class,
        parentColumns = ["id"],
        childColumns = ["order_id"]),
    ForeignKey(
        entity = Seller::class,
        parentColumns = ["id"],
        childColumns = ["seller_id"]),
    ForeignKey(
        entity = Costumer::class,
        parentColumns = ["id"],
        childColumns = ["costumer_id"]),
    ForeignKey(
        entity = Promotion::class,
        parentColumns = ["id"],
        childColumns = ["promotion_id"]),
    ForeignKey(
        entity = PaymentMethod::class,
        parentColumns = ["id"],
        childColumns = ["paymentMethod_id"]),
]
)
data class Sale(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "seller_id") val sellerId: Long,
    @ColumnInfo(name = "costumer_id") val costumerId: Long,
    @ColumnInfo(name = "shipping_id") val shippingId: Long? = null,
    @ColumnInfo(name = "order_id") val orderId: Long? = null,
    @ColumnInfo(name = "paymentMethod_id") val paymentMethodId: Long,
)
