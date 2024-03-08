package com.chrrissoft.room.categories.view.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chrrissoft.room.categories.db.objects.CategoryWithRelationship
import com.chrrissoft.room.shared.app.ResState
import com.chrrissoft.room.ui.components.MyModalBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndOrRemoveCategoryListSheet(
    added: ResState<Map<String, CategoryWithRelationship>>,
    available: ResState<Map<String, CategoryWithRelationship>>,
    onRemove: (Map<String, CategoryWithRelationship>) -> Unit,
    onAdd: (Map<String, CategoryWithRelationship>) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MyModalBottomSheet(
        onDismissRequest = onDismissRequest,
        content = { AndOrRemoveCategoryList(added, available, onRemove, onAdd) },
        modifier = modifier
    )
}
