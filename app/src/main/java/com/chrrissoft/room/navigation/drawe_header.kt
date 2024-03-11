package com.chrrissoft.room.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.unit.dp
import com.chrrissoft.room.R

@Composable
fun DrawerHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val size = LocalConfiguration.current.screenWidthDp.div(2).dp
        Image(
            contentDescription = null,
            painter = painterResource(id = R.drawable.room_iocn),
            modifier = Modifier.padding(vertical = 20.dp).size(size)
        )
        Text(
            text = "Room App",
            style = typography.displaySmall,
            fontWeight = ExtraBold,
            color = colorScheme.primary,
            modifier = Modifier.align(Alignment.Start)
        )
    }
}
