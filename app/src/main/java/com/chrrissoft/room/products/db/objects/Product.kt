package com.chrrissoft.room.products.db.objects

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chrrissoft.room.promotions.db.objects.Promotion


@Entity(
    "products", foreignKeys = [
        ForeignKey(
            entity = Promotion::class,
            parentColumns = ["id"],
            childColumns = ["promotion_id"],
            // onUpdate = ForeignKey.CASCADE,
            // onDelete =  ForeignKey.SET_DEFAULT,
        ),
    ]
)
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "description") val description: String = "",
    @ColumnInfo(name = "promotion_id", defaultValue = "NULL") val promotionId: String? = null,
)
