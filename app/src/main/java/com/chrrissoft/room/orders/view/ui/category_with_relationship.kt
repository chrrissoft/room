package com.chrrissoft.room.orders.view.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.chrrissoft.room.cities.db.objects.CityWithRelationship
import com.chrrissoft.room.cities.view.ui.CitiesListSheet
import com.chrrissoft.room.orders.db.objects.OrderWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.shared.view.ResState
import com.chrrissoft.room.ui.components.RoomDivider
import com.chrrissoft.room.ui.components.SelectableRoomTextField
import com.chrrissoft.room.utils.PairUtils.mapSecond

@Composable
fun OrderWithRelationship(
    state: ResState<Pair<String, OrderWithRelationship>>,
    onStateChange: (Pair<String, OrderWithRelationship>) -> Unit,
    cities: ResState<Map<String, CityWithRelationship>>,
    modifier: Modifier = Modifier,
) {
    ResState(state = state) { pair ->
        var showCities by remember { mutableStateOf(value = false) }

        if (showCities) {
            CitiesListSheet(
                state = cities,
                selected = setOf(pair.second.city.id),
                onDelete = { },
                onSelect = { onStateChange(pair.mapSecond { copy(city = it.second.city)}) },
                onDismissRequest = { showCities = false })
        }


        Column(modifier) {
            Order(
                state = pair.second.order,
                onStateChange = { onStateChange(pair.mapSecond { copy(order = it)}) })
            RoomDivider()
            SelectableRoomTextField(value = pair.second.city.name, onClick = {})
//        SelectableRoomTextField(value = state.sale.seller.name.firstName, onClick = {})
//        SelectableRoomTextField(value = state.sale.costumer.name.firstName, onClick = {})
            SelectableRoomTextField(value = pair.second.shipping.state.name, onClick = {})
        }
    }
}
