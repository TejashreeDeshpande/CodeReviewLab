package com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository

import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.BatteryTelemetry
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for vehicle battery telemetry.
 *
 * This abstraction enables easier unit testing and allows for different
 * implementations (e.g., Bluetooth/VSS, Cloud API, or Mock).
 */
interface BatteryRepository {
    /**
     * Returns a [Flow] that periodically emits the latest vehicle battery status.
     * The stream handles its own polling logic and emission context.
     */
    fun observeBatteryStatus(): Flow<BatteryTelemetry>

    /**
     * Performs a one-time refresh of the battery data.
     *
     * @return The latest [BatteryTelemetry] available.
     */
    suspend fun refreshNow(): BatteryTelemetry
}