package com.tejashree.codereviewlab.features.vehicle.smartparking.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

@Stable
class SmartParkingViewModel : ViewModel() {
    // Target timestamp when parking expires
    private var expirationTimestamp = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5058L)

    private val _remainingTime = MutableStateFlow("")
    val remainingTime: StateFlow<String> = _remainingTime.asStateFlow()

    private val _expirationTime = MutableStateFlow("")
    val expirationTime: StateFlow<String> = _expirationTime.asStateFlow()

    init {
        updateTimes()
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                updateTimes()
                delay(1000.milliseconds)
            }
        }
    }

    private fun updateTimes() {
        val currentMillis = System.currentTimeMillis()
        val remainingMillis = (expirationTimestamp - currentMillis).coerceAtLeast(0)
        val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(remainingMillis)
        
        _remainingTime.value = formatTime(remainingSeconds)
        _expirationTime.value = formatExpirationTime(expirationTimestamp)
    }

    private fun formatTime(seconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(seconds)
        val minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60
        val secs = seconds % 60
        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, secs)
    }

    private fun formatExpirationTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.US)
        return "Expires at ${sdf.format(Date(timestamp))}"
    }

    fun extendParking() {
        // Add 15 minutes to the target expiration timestamp
        expirationTimestamp += TimeUnit.MINUTES.toMillis(15)
        updateTimes()
    }
}
