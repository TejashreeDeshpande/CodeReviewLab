package com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model

data class BatteryTelemetry(
    val batteryPercent: Int,
    val estimatedRangeMiles: Int,
    val chargingStatus: ChargingStatus,
    val chargingPowerKw: Double,
    val timeToFull: String,
    val updatedAgo: String,
    val isFresh: Boolean
)

enum class ChargingStatus {
    CHARGING,
    DISCHARGING,
    COMPLETE,
    STALE
}