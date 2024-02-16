package com.chrrissoft.room.reviews.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.costumers.db.objects.Costumer
import com.chrrissoft.room.products.db.objects.Product

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = Costumer::class,
            parentColumns = ["id"],
            childColumns = ["costumer_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.RESTRICT,
        ),
    ]
)
data class Review(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "start") val start: Float,
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "costumer_id") val costumerId: String,
) {
    companion object {
        val startsRange = 1f..5f
    }
}
