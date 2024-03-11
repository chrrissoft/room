package com.chrrissoft.room.sales.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.payments.view.ui.Payment
import com.chrrissoft.room.sellers.db.objects.Seller


@Entity(
    tableName = "sales", foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["id"],
            childColumns = ["order_id"],
            // onUpdate = ForeignKey.CASCADE,
            // onDelete =  ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = Seller::class,
            parentColumns = ["id"],
            childColumns = ["seller_id"],
            // onUpdate = ForeignKey.CASCADE,
            // onDelete =  ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = Costumer::class,
            parentColumns = ["id"],
            childColumns = ["costumer_id"],
            // onUpdate = ForeignKey.CASCADE,
            // onDelete =  ForeignKey.RESTRICT,
        ),
    ]
)
data class Sale(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "payment_method") val payment: Payment = Payment.CASH,
    @ColumnInfo(name = "seller_id") val sellerId: String = "",
    @ColumnInfo(name = "costumer_id") val costumerId: String = "",
    @ColumnInfo(name = "order_id") val orderId: String? = null,
) {
    companion object {
        val invalid = Sale("")
    }
}
