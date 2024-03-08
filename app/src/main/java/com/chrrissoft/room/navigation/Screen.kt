package com.chrrissoft.room.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ListAlt
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Factory
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Flag
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.Groups
import androidx.compose.material.icons.rounded.Inventory
import androidx.compose.material.icons.rounded.LocalShipping
import androidx.compose.material.icons.rounded.ProductionQuantityLimits
import androidx.compose.material.icons.rounded.RealEstateAgent
import androidx.compose.ui.graphics.vector.ImageVector
import com.chrrissoft.room.R

sealed class Screen(val baseRoute: String, val label: Int, val icon: ImageVector) {
    abstract val route: String

    object Carriers : Screen(("Carriers"), R.string.carriers, Icons.Rounded.Group) {
        override val route = baseRoute
    }

    object Categories : Screen(("Categories"), R.string.categories, Icons.Rounded.Category) {
        override val route = baseRoute
    }

    object Cities : Screen(("Cities"), R.string.cities, Icons.Rounded.Favorite) {
        override val route = baseRoute
    }

    object Costumers : Screen(("Costumers"), R.string.costumers, Icons.Rounded.Groups) {
        override val route = baseRoute
    }

    object Countries : Screen(("Countries"), R.string.countries, Icons.Rounded.Flag) {
        override val route = baseRoute
    }

    object Orders : Screen(("Orders"), R.string.orders, Icons.AutoMirrored.Rounded.ListAlt) {
        override val route = baseRoute
    }

    object Products : Screen(("Products"), R.string.products, Icons.Rounded.Inventory) {
        override val route = baseRoute
    }

    object Promotions : Screen(("Promotions"), R.string.promotions, Icons.Rounded.ProductionQuantityLimits) {
        override val route = baseRoute
    }

    object Sales : Screen(("Sales"), R.string.sales, Icons.Rounded.RealEstateAgent) {
        override val route = baseRoute
    }

    object Sellers : Screen(("Sellers"), R.string.sellers, Icons.Rounded.Group) {
        override val route = baseRoute
    }

    object Shipments : Screen(("Shipments"), R.string.shipments, Icons.Rounded.LocalShipping) {
        override val route = baseRoute
    }

    object Suppliers : Screen(("Suppliers"), R.string.suppliers, Icons.Rounded.Factory) {
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
