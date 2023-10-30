package com.chrrissoft.room.products

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.categories.Category
import com.chrrissoft.room.inventories.Inventory


@Entity("products", foreignKeys = [
    ForeignKey(
    entity = Category::class,
    parentColumns = ["id"],
    childColumns = ["category_id"]),
    ForeignKey(
        entity = Inventory::class,
        parentColumns = ["id"],
        childColumns = ["inventory_id"]),
])
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "category_id") val categoryId: Long,
    @ColumnInfo(name = "inventory_id") val inventoryId: Long,
)
