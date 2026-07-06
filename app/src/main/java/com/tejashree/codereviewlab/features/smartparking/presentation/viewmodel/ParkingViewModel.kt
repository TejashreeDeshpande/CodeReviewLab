package com.tejashree.codereviewlab.features.smartparking.presentation.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

@Stable
class ParkingViewModel : ViewModel() {
    private var totalSeconds = 5058L // 01:24:18 in seconds

    private val _remainingTime = MutableStateFlow(formatTime(totalSeconds))
    val remainingTime: StateFlow<String> = _remainingTime.asStateFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (totalSeconds > 0) {
                delay(1000.milliseconds)
                totalSeconds--
                _remainingTime.value = formatTime(totalSeconds)
            }
        }
    }

    private fun formatTime(seconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(seconds)
        val minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60
        val secs = seconds % 60
        return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, secs)
    }

    fun extendParking() {
        totalSeconds += 15 * 60
        _remainingTime.value = formatTime(totalSeconds)
    }
}
