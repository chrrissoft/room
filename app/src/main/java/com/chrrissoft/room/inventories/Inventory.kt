package com.chrrissoft.room.inventories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.products.Product

@Entity(tableName = "inventories", foreignKeys = [ForeignKey(
    entity = Product::class,
    parentColumns = ["id"],
    childColumns = ["product_id"]
)])
data class Inventory(
    @PrimaryKey
    @ColumnInfo(name = "product_id") val productId: Long,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "direction") val direction: Direction
) {
    sealed interface Direction {
        data class Hold(@ColumnInfo(name = "number") val number: Int) : Direction
        data class Aisle(@ColumnInfo(name = "number") val number: Int) : Direction
    }
}
