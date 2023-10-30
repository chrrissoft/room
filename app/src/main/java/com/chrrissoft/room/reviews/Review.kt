package com.chrrissoft.room.reviews

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.costumers.Costumer
import com.chrrissoft.room.products.Product

@Entity(tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]),
        ForeignKey(
            entity = Costumer::class,
            parentColumns = ["id"],
            childColumns = ["costumer_id"])
    ])
data class Review(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "starts") val start: Starts,
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "costumer_id") val costumerId: Long,
) {

    companion object {
        val startsRange = 1f..5f
    }

    data class Starts(val start: Float) {
        init {
            if (!startsRange.contains(start))
                throw IllegalArgumentException(
                    "start must be in range between ${startsRange.start} to ${startsRange.endInclusive}"
                )
        }
    }
}
