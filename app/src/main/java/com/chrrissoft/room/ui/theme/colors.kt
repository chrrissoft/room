package com.chrrissoft.room.ui.theme

import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.InputChipDefaults.inputChipColors
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import com.chrrissoft.room.ui.components.AlertDialogColors
import androidx.compose.material3.NavigationBarItemDefaults.colors as navigationBarItemColors

@OptIn(ExperimentalMaterial3Api::class)
val centerAlignedTopAppBarColors
    @Composable get() = run {
        centerAlignedTopAppBarColors(
            containerColor = colorScheme.primaryContainer,
            navigationIconContentColor = colorScheme.primary,
            titleContentColor = colorScheme.primary,
            actionIconContentColor = colorScheme.primary,
        )
    }

val navigationBarItemColors
    @Composable get() = run {
        navigationBarItemColors(
            selectedIconColor = colorScheme.onPrimary,
            selectedTextColor = colorScheme.primary,
            indicatorColor = colorScheme.primary,
            unselectedIconColor = colorScheme.primary.copy(.5f),
            unselectedTextColor = colorScheme.primary.copy(.5f),
        )
    }

val navigationDrawerItemColors
    @Composable get() = run {
        NavigationDrawerItemDefaults.colors(
            selectedContainerColor = colorScheme.primary,
            unselectedContainerColor = Transparent,
            selectedIconColor = colorScheme.onPrimary,
            unselectedIconColor = colorScheme.primary,
            selectedTextColor = colorScheme.onPrimary,
            unselectedTextColor = colorScheme.primary,
        )
    }

@OptIn(ExperimentalMaterial3Api::class)
val inputChipColors
    @Composable get() = run {
        inputChipColors(
            containerColor = colorScheme.primaryContainer,
            labelColor = colorScheme.primary,
            leadingIconColor = colorScheme.primary,
            trailingIconColor = colorScheme.primary,
            disabledContainerColor = colorScheme.primaryContainer.copy(.5f),
            disabledLabelColor = colorScheme.secondary.copy(.5f),
            disabledLeadingIconColor = colorScheme.secondary.copy(.5f),
            disabledTrailingIconColor = colorScheme.secondary.copy(.5f),
            selectedContainerColor = colorScheme.primary,
            disabledSelectedContainerColor = colorScheme.errorContainer,
            selectedLabelColor = colorScheme.onPrimary,
            selectedLeadingIconColor = colorScheme.onPrimary,
            selectedTrailingIconColor = colorScheme.onPrimary,
        )
    }

val cardColors
    @Composable get() = run {
        cardColors(
            containerColor = colorScheme.primaryContainer,
            contentColor = colorScheme.onPrimaryContainer,
        )
    }

@Composable
fun textFieldColors(
    focusedTextColor: Color = colorScheme.primary,
    unfocusedTextColor: Color = colorScheme.primary,
    disabledTextColor: Color = colorScheme.primary,
    focusedContainerColor: Color = colorScheme.primaryContainer,
    unfocusedContainerColor: Color = colorScheme.primaryContainer,
    disabledContainerColor: Color = colorScheme.primaryContainer,
    cursorColor: Color = colorScheme.primary,
    focusedIndicatorColor: Color = Transparent,
    unfocusedIndicatorColor: Color = Transparent,
    disabledIndicatorColor: Color = Transparent,
    focusedTrailingIconColor: Color = colorScheme.primary,
    unfocusedTrailingIconColor: Color = colorScheme.primary,
    disabledTrailingIconColor: Color = colorScheme.primary,
    focusedLeadingIconColor: Color = colorScheme.primary,
    unfocusedLeadingIconColor: Color = colorScheme.primary,
    disabledLeadingIconColor: Color = colorScheme.primary,
    focusedLabelColor: Color = colorScheme.primary.copy((.6f)),
    unfocusedLabelColor: Color = colorScheme.primary.copy((.6f)),
    disabledLabelColor: Color = colorScheme.primary.copy((.6f)),
): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = focusedTextColor,
        unfocusedTextColor = unfocusedTextColor,
        disabledTextColor = disabledTextColor,
        focusedContainerColor = focusedContainerColor,
        unfocusedContainerColor = unfocusedContainerColor,
        disabledContainerColor = disabledContainerColor,
        cursorColor = cursorColor,
        focusedIndicatorColor = focusedIndicatorColor,
        unfocusedIndicatorColor = unfocusedIndicatorColor,
        disabledIndicatorColor = disabledIndicatorColor,
        focusedTrailingIconColor = focusedTrailingIconColor,
        unfocusedTrailingIconColor = unfocusedTrailingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        focusedLeadingIconColor = focusedLeadingIconColor,
        unfocusedLeadingIconColor = unfocusedLeadingIconColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        disabledLabelColor = disabledLabelColor,
    )
}

@Composable
fun selectableTextFieldColors(selected: Boolean): TextFieldColors {
    return textFieldColors(
        disabledTextColor = if (selected) colorScheme.onPrimary else colorScheme.primary,
        disabledLabelColor = if (selected) colorScheme.onPrimary.copy((.6f)) else colorScheme.primary.copy((.6f)),
        disabledLeadingIconColor = if (selected) colorScheme.onPrimary else colorScheme.primary,
        disabledTrailingIconColor = if (selected) colorScheme.onPrimary else colorScheme.primary,
        disabledContainerColor = if (selected) colorScheme.primary else colorScheme.primaryContainer,
    )
}

val alertDialogColors
    @Composable get() = run {
        AlertDialogColors.colors(
            containerColor = colorScheme.primaryContainer,
            iconContentColor = colorScheme.onPrimaryContainer,
            titleContentColor = colorScheme.onPrimaryContainer,
            textContentColor = colorScheme.onPrimaryContainer,
        )
    }


