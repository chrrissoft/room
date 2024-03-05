package com.chrrissoft.room.sellers.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.sales.db.objects.SaleWithRelationship
import com.chrrissoft.room.sales.view.ui.SaleListSheet
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun SellerWithRelationship(
    state: ResState<Pair<String, SellerWithRelationship>>,
    onStateChange: (Pair<String, SellerWithRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    sales: ResState<Map<String, SaleWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { data ->

        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            CitiesListSheet(
                state = cities,
                selected = setOf(data.second.city.id),
                onSelect = {
                    val seller = data.second.seller.copy(cityId = it.first)
                    data.mapSecond { copy(seller = seller, city = it.second.city) }
                        .let(onStateChange)
                },
                onDismissRequest = { showCities = false },
            )
        }


        var showSales by remember { mutableStateOf(value = false) }

        if (showSales) {
            val selected =
                remember(data.second.sales) { data.second.sales.mapTo(mutableSetOf()) { it.id } }
            SaleListSheet(
                state = sales,
                onSelect = { sale ->
                    (if (data.second.sales.contains(sale.second.sale)) data.second.sales.minus(sale.second.sale)
                    else data.second.sales.plus(sale.second.sale)).let {
                        onStateChange(data.mapSecond {
                            copy(
                                sales = it
                            )
                        })
                    }
                },
                selected = selected,
                onDelete = {},
                onDismissRequest = { showSales = false })
        }


        Column(modifier) {
            Seller(
                state = data.second.seller,
                onStateChange = { onStateChange(data.mapSecond { copy(seller = it) }) })
            RoomDivider()
            SelectableRoomTextField(
                label = "Cities",
                value = data.second.city.name,
                selected = showCities,
                onClick = { showCities = true }
            )
            SelectableRoomTextField(
                label = "Sales",
                value = data.second.sales.map { it.id }.joinToString { "," },
                selected = showSales,
                onClick = { showSales = true }
            )
        }
    }
}
