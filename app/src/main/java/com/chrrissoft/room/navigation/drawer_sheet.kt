package com.chrrissoft.room.navigation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DrawerSheet(
    currentScreen: String?,
    onNav: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(modifier, drawerContainerColor = colorScheme.primaryContainer) {
        DrawerHeader(Modifier.padding(horizontal = 10.dp))
        Spacer(modifier = Modifier.height(10.dp))
        DrawerItems(screens = Screen.screens, currentScreen = currentScreen) { onNav(it) }
    }
}
