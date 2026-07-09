package com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model

data class BatteryUiState(
    val isLoading: Boolean = true,
    val telemetry: BatteryTelemetry? = null,
    val error: String? = null
)