package com.chrrissoft.room.sellers.db.objects

import androidx.room.Embedded
import androidx.room.Relation
import com.chrrissoft.room.cities.db.objects.City
import com.chrrissoft.room.orders.db.objects.Order
import com.chrrissoft.room.sales.db.objects.Sale

data class SellerWithRelationship(
    @Embedded val seller: Seller,
    @Relation(parentColumn = "city_id", entityColumn = "id")
    val city: City,
    @Relation(parentColumn = "id", entityColumn = "seller_id")
    val sales: List<Sale>,
    @Relation(parentColumn = "id", entityColumn = "seller_id")
    val orders: List<Order>,
)
