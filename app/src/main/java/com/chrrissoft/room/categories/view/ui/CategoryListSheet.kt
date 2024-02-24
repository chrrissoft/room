package com.chrrissoft.room.categories.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState

@Composable
fun CategoryListSheet(
    state: ResState<Map<String, CategoryWithRelationship>>,
    onSelect: (Pair<String, CategoryWithRelationship>) -> Unit,
    onDelete: (Map<String, CategoryWithRelationship>) -> Unit,
    selected: Set<String>,
    onDismissRequest: () -> Unit
) {

}
