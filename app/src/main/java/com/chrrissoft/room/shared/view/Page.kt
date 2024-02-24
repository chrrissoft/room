package com.chrrissoft.room.shared.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.List
import androidx.compose.material.icons.rounded.Details
import com.chrrissoft.room.R
import com.chrrissoft.room.ui.entities.Page

enum class Page : Page {
    LIST {
        override val icon = Icons.AutoMirrored.Rounded.List
        override val label = R.string.list_page_label
    },
    DETAIL {
        override val icon = Icons.Rounded.Details
        override val label = R.string.detals_page_label
    },;

    companion object {
        val pages = listOf(LIST, DETAIL)
    }
}
