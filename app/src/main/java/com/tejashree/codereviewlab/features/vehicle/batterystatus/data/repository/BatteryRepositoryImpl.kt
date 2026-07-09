package com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository

import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.BatteryTelemetry
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.ChargingStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.atomic.AtomicInteger
import kotlin.time.Duration.Companion.milliseconds


/**
 * Implementation of [BatteryRepository] that simulates vehicle telemetry.
 *
 * @param ioDispatcher The [CoroutineDispatcher] to use for polling and emissions.
 */
class BatteryRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BatteryRepository {

    // Thread-safe internal state tracking the current battery percentage
    private val batteryLevel = AtomicInteger(INITIAL_BATTERY_LEVEL)

    override fun observeBatteryStatus(): Flow<BatteryTelemetry> = flow {
        while (true) {
            val current = updateAndGetBatteryLevel()
            emit(createTelemetry(current))
            delay(POLL_INTERVAL_MS.milliseconds)
        }
    }
    .distinctUntilChanged()
    .flowOn(ioDispatcher)

    override suspend fun refreshNow(): BatteryTelemetry {
        // In a real-world scenario, this would involve a network or hardware request.
        // We simulate this by returning the latest state immediately with a 'fresh' flag.
        return createTelemetry(batteryLevel.get(), isFresh = true)
    }

    private fun updateAndGetBatteryLevel(): Int {
        return batteryLevel.updateAndGet { level ->
            (level + 1).coerceAtMost(MAX_BATTERY_LEVEL)
        }
    }

    private fun createTelemetry(level: Int, isFresh: Boolean = true): BatteryTelemetry {
        return BatteryTelemetry(
            batteryPercent = level,
            estimatedRangeMiles = level * MILES_PER_PERCENT,
            chargingStatus = if (level >= MAX_BATTERY_LEVEL) ChargingStatus.COMPLETE else ChargingStatus.CHARGING,
            chargingPowerKw = DEFAULT_CHARGING_POWER_KW,
            timeToFull = calculateTimeToFull(level),
            updatedAgo = if (isFresh) "Updated just now" else "Updated recently",
            isFresh = isFresh
        )
    }

    private fun calculateTimeToFull(level: Int): String {
        if (level >= MAX_BATTERY_LEVEL) return "Full"
        // Mocked calculation: 5 minutes per percentage point
        val totalMinutes = (MAX_BATTERY_LEVEL - level) * 5
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return if (hours > 0) "$hours h $minutes min" else "$minutes min"
    }

    companion object {
        private const val INITIAL_BATTERY_LEVEL = 72
        private const val MAX_BATTERY_LEVEL = 90
        private const val MILES_PER_PERCENT = 3
        private const val DEFAULT_CHARGING_POWER_KW = 7.2
        private const val POLL_INTERVAL_MS = 3000L
    }
}
