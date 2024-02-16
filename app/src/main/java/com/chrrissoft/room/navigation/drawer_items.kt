package com.chrrissoft.room.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chrrissoft.room.ui.theme.navigationDrawerItemColors

@Composable
fun DrawerItems(
    screens: List<Screen>,
    currentScreen: String?,
    onNav: (String) -> Unit,
) {
    LazyColumn {
        items(screens) {
            val selected = currentScreen == it.baseRoute
            val weight = if (selected) FontWeight.Bold else FontWeight.Normal
            NavigationDrawerItem(
                selected = selected,
                icon = { Icon(it.icon, (null)) },
                label = { Text(text = stringResource(it.label), fontWeight = weight) },
                onClick = { onNav(it.baseRoute) },
                colors = navigationDrawerItemColors
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}