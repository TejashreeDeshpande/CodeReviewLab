package com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.BatteryTelemetry
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.ChargingStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds

/**
 * Implementation of [BatteryRepository] that simulates vehicle telemetry using DataStore.
 * This implementation is main-safe and persists state across sessions.
 *
 * @param dataStore The [DataStore] used to persist battery level.
 */
class BatteryRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : BatteryRepository {

    override fun observeBatteryStatus(): Flow<BatteryTelemetry> = flow {
        while (true) {
            val current = updateAndGetBatteryLevel()
            emit(createTelemetry(current))
            delay(POLL_INTERVAL_MS.milliseconds)
        }
    }
    .distinctUntilChanged()

    override suspend fun refreshNow(): BatteryTelemetry {
        val current = dataStore.data.first()[BATTERY_PERCENT] ?: INITIAL_BATTERY_LEVEL
        return createTelemetry(current, isFresh = true)
    }

    private suspend fun updateAndGetBatteryLevel(): Int {
        return dataStore.edit { prefs ->
            val level = prefs[BATTERY_PERCENT] ?: INITIAL_BATTERY_LEVEL
            prefs[BATTERY_PERCENT] = (level + 1).coerceAtMost(MAX_BATTERY_LEVEL)
        }[BATTERY_PERCENT] ?: INITIAL_BATTERY_LEVEL
    }

    private fun createTelemetry(level: Int, isFresh: Boolean = false): BatteryTelemetry {
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
        val totalMinutes = (MAX_BATTERY_LEVEL - level) * 5
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return if (hours > 0) "$hours h $minutes min" else "$minutes min"
    }

    companion object {
        private val BATTERY_PERCENT = intPreferencesKey("battery_percent")
        private const val INITIAL_BATTERY_LEVEL = 72
        private const val MAX_BATTERY_LEVEL = 90
        private const val MILES_PER_PERCENT = 3
        private const val DEFAULT_CHARGING_POWER_KW = 7.2
        private const val POLL_INTERVAL_MS = 3000L
    }
}
