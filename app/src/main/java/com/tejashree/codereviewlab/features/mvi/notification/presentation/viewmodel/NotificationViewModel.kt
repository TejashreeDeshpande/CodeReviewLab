package com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.mvi.notification.data.model.SmartNotification
import com.tejashree.codereviewlab.features.mvi.notification.data.model.sampleNotifications
import com.tejashree.codereviewlab.features.mvi.notification.presentation.LocalNotificationHelper
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NotificationViewModel(
    private val notificationHelper: LocalNotificationHelper
) : ViewModel() {
    private val _state = MutableStateFlow(
        NotificationState(
            notifications = sampleNotifications.toImmutableList()
        )
    )

    val state = _state.asStateFlow()

    /**
     * Derived state for the UI to display.
     * Moved from Compose 'remember' to ViewModel for better performance and separation of concerns.
     */
    val filteredNotifications = state.map { s ->
        s.notifications
            .filter {
                val matchesFilter = when (s.filter) {
                    NotificationFilter.ACTIVE -> !it.archived && !it.snoozed
                    NotificationFilter.ARCHIVED -> it.archived
                    NotificationFilter.SNOOZED -> it.snoozed
                }
                matchesFilter && (s.selectedPriority == null || it.priority == s.selectedPriority)
            }
            .sortedByDescending { it.createdAt }
            .toImmutableList()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = persistentListOf()
    )

    fun process(intent: NotificationIntent) {
        when (intent) {
            is NotificationIntent.LoadNotifications -> {
                // Handle loading logic here
            }

            is NotificationIntent.Filter -> {
                _state.update {
                    it.copy(selectedPriority = intent.priority)
                }
            }

            is NotificationIntent.SetFilter -> {
                _state.update {
                    it.copy(filter = intent.filter)
                }
            }

            is NotificationIntent.Archive -> {
                _state.update {
                    it.copy(
                        notifications = it.notifications.map { notification ->
                            if (notification.id == intent.id)
                                notification.copy(archived = true)
                            else
                                notification
                        }.toImmutableList()
                    )
                }
            }

            is NotificationIntent.Snooze -> {
                _state.update {
                    it.copy(
                        notifications = it.notifications.map { notification ->
                            if (notification.id == intent.id)
                                // Implementation note: For a real app, we would add a 'snoozedUntil' timestamp
                                // and use WorkManager to re-trigger the notification.
                                notification.copy(snoozed = true)
                            else
                                notification
                        }.toImmutableList()
                    )
                }
            }

            is NotificationIntent.Delete -> {
                _state.update {
                    it.copy(
                        notifications = it.notifications.filterNot { notification ->
                            notification.id == intent.id
                        }.toImmutableList()
                    )
                }
            }

            is NotificationIntent.CreateNotification -> {
                val now = System.currentTimeMillis()
                val remindAt = intent.reminderDelayMs?.let { now + it }
                
                val newNotification = SmartNotification(
                    id = now.toInt(),
                    title = intent.title,
                    message = intent.message,
                    priority = intent.priority,
                    type = intent.type,
                    time = if (remindAt != null) "Scheduled" else "Just now",
                    createdAt = now,
                    remindAt = remindAt
                )

                _state.update {
                    it.copy(
                        notifications = (listOf(newNotification) + it.notifications).toImmutableList()
                    )
                }

                if (intent.reminderDelayMs != null) {
                    notificationHelper.scheduleNotification(newNotification, intent.reminderDelayMs)
                } else {
                    notificationHelper.showNotification(newNotification)
                }
            }
        }
    }
}