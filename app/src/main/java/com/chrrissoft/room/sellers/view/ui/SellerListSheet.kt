package com.chrrissoft.room.sellers.view.ui

import androidx.compose.runtime.Composable
import com.chrrissoft.room.sellers.db.objects.SellerWithRelationship

@Composable
fun SellerListSheet(
    state: List<SellerWithRelationship>,
    selected: Set<String>,
    onSelect: (SellerWithRelationship) -> Unit,
    onDismissRequest: () -> Unit
) {

}
