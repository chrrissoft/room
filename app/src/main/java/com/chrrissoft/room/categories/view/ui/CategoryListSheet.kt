package com.chrrissoft.room.categories.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship

@Composable
fun CategoryListSheet(
    state: List<CategoryWithRelationship>,
    onSelect: (CategoryWithRelationship) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit
) {

}