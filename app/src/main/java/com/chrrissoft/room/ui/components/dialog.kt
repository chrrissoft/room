package com.chrrissoft.room.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties
import com.chrrissoft.room.ui.theme.alertDialogColors
import androidx.compose.material3.AlertDialog as AndroidAlertDialog

data class AlertDialogColors(
    val containerColor: Color,
    val iconContentColor: Color,
    val titleContentColor: Color,
    val textContentColor: Color,
) {
    companion object {
        @Composable
        fun colors(
            containerColor: Color = AlertDialogDefaults.containerColor,
            iconContentColor: Color = AlertDialogDefaults.iconContentColor,
            titleContentColor: Color = AlertDialogDefaults.titleContentColor,
            textContentColor: Color = AlertDialogDefaults.textContentColor,
        ): AlertDialogColors {
            return AlertDialogColors(
                containerColor,
                iconContentColor,
                titleContentColor,
                textContentColor,
            )
        }
    }
}
