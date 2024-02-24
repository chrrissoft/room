package com.chrrissoft.room.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chrrissoft.room.shared.view.Page

@Composable
fun CommonScreen(
    modifier: Modifier = Modifier,
    title: String,
    onNavigation: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    page: Page,
    onChangePage: (Page) -> Unit,
    onSave: () -> Unit,
    onCreate: (String) -> Unit,
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = colorScheme.onPrimary,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    list: @Composable ColumnScope.(PaddingValues) -> Unit,
    details: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    Screen(
        modifier = modifier,
        title = title,
        onNavigation = onNavigation,
        actions = actions,
        bottomBar = {
            PagesBottomBar(
                page = page,
                pages = Page.pages,
                onChangePage = { onChangePage(it) }
            )
        },
        snackbarHost = snackbarHost,
        floatingActionButton = {
            page.SaveFloatingActionButton(onSave = onSave, onCreate = onCreate)
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = {
            when (page) {
                Page.LIST -> list(it)
                Page.DETAIL -> details(it)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    title: String,
    onNavigation: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = colorScheme.onPrimary,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = title,
                onNavigation = onNavigation,
                actions = actions,
            )
        },
        bottomBar = bottomBar,
        snackbarHost = snackbarHost,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = {
            Column(
                Modifier
                    .padding(it)
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
            ) { content(it) }
        },
    )
}