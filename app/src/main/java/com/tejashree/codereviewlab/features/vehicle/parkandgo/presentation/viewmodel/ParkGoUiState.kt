package com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.viewmodel

import androidx.compose.runtime.Immutable
import com.tejashree.codereviewlab.features.vehicle.parkandgo.data.CheckItem

@Immutable
data class ParkGoUiState(
    val items: List<CheckItem> = listOf(
        CheckItem(1, "Wallet", "💳"),
        CheckItem(2, "Laptop", "💻"),
        CheckItem(3, "Keys", "🔑"),
        CheckItem(4, "Child seat", "👶")
    ),
    val parkedAt: String = ""
) {
    val allChecked: Boolean
        get() = items.all { it.checked }
}
