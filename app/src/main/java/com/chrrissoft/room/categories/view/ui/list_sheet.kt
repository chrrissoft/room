package com.chrrissoft.room.categories.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListSheet(
    state: ResState<Map<String, CategoryWithRelationship>>,
    onSelect: (Pair<String, CategoryWithRelationship>) -> Unit,
    selected: Set<String?>,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onDelete: ((Map<String, CategoryWithRelationship>) -> Unit)? = null,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { CategoriesList(state, selected, onSelect, onDelete = onDelete) },
        modifier = modifier
    )
}
