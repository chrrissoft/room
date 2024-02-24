package com.chrrissoft.room.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import com.chrrissoft.room.R

sealed class Screen(val baseRoute: String, val label: Int, val icon: ImageVector) {
    abstract val route: String

    object Carriers : Screen(("Carriers"), R.string.carriers, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Categories : Screen(("Categories"), R.string.categories, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Cities : Screen(("Cities"), R.string.cities, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Costumers : Screen(("Costumers"), R.string.costumers, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Countries : Screen(("Countries"), R.string.countries, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Orders : Screen(("Orders"), R.string.orders, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Products : Screen(("Products"), R.string.products, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Promotions : Screen(("Promotions"), R.string.promotions, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Sales : Screen(("Sales"), R.string.sales, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Sellers : Screen(("Sellers"), R.string.sellers, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Shipments : Screen(("Shipments"), R.string.shipments, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Suppliers : Screen(("Suppliers"), R.string.suppliers, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }


    companion object {
        val screens get() = buildList {
            add(Carriers)
            add(Categories)
            add(Cities)
            add(Costumers)
            add(Countries)
            add(Orders)
            add(Products)
            add(Promotions)
            add(Sales)
            add(Sellers)
            add(Shipments)
            add(Suppliers)
        }
    }
}
