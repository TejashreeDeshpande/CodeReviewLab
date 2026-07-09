package com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.tejashree.codereviewlab.features.vehicle.parkandgo.data.CheckItem
import com.tejashree.codereviewlab.features.vehicle.parkandgo.util.toParkAndGoTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar

/**
 * ViewModel for the Park & Go feature.
 * Manages the state of the parking location and the pre-departure checklist.
 */
class ParkGoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        ParkGoUiState(parkedAt = Calendar.getInstance().toParkAndGoTimestamp())
    )
    val uiState: StateFlow<ParkGoUiState> = _uiState.asStateFlow()

    /**
     * Toggles the checked status of a checklist item.
     * @param id The unique identifier of the item to toggle.
     */
    fun toggleItem(id: Int) {
        _uiState.update { state ->
            state.copy(
                items = state.items.map { item ->
                    if (item.id == id) {
                        item.copy(checked = !item.checked)
                    } else {
                        item
                    }
                }
            )
        }
    }

    /**
     * Toggles all checklist items to either all checked or all unchecked.
     */
    fun toggleAll() {
        _uiState.update { state ->
            val targetChecked = !state.allChecked
            state.copy(
                items = state.items.map { it.copy(checked = targetChecked) }
            )
        }
    }

    /**
     * Adds a new custom item to the checklist.
     * @param title The title of the new item.
     */
    fun addItem(title: String) {
        if (title.isBlank()) return

        _uiState.update { state ->
            state.copy(
                items = state.items + CheckItem(
                    id = state.items.size + 1,
                    title = title,
                    icon = "\uD83D\uDCE6"
                )
            )
        }
    }

}
