package com.tejashree.codereviewlab.features.vehicle.batterystatus.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.BatteryUiState
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository.BatteryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BatteryViewModel(
    private val repository: BatteryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BatteryUiState())
    val uiState: StateFlow<BatteryUiState> = _uiState

    init {
        observeBattery()
    }

    private fun observeBattery() {
        viewModelScope.launch {
            repository.observeBatteryStatus()
                .onStart {
                    _uiState.value = BatteryUiState(isLoading = true)
                }
                .catch {
                    _uiState.value = BatteryUiState(
                        isLoading = false,
                        error = "Unable to load battery status"
                    )
                }
                .collect { telemetry ->
                    _uiState.value = BatteryUiState(
                        isLoading = false,
                        telemetry = telemetry
                    )
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val latest = repository.refreshNow()
            _uiState.value = BatteryUiState(
                isLoading = false,
                telemetry = latest
            )
        }
    }
}