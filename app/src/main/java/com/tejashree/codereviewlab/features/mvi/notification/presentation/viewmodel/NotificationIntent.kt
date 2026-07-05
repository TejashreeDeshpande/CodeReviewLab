package com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel

import com.tejashree.codereviewlab.features.mvi.notification.data.model.NotificationType
import com.tejashree.codereviewlab.features.mvi.notification.data.model.Priority

sealed interface NotificationIntent {
    data class Filter(
        val priority: Priority?
    ): NotificationIntent

    data class SetFilter(
        val filter: NotificationFilter
    ): NotificationIntent

    data class Archive(
        val id: Int
    ): NotificationIntent

    data class Snooze(
        val id: Int
    ): NotificationIntent

    data class Delete(
        val id: Int
    ): NotificationIntent

    data object LoadNotifications : NotificationIntent

    data class CreateNotification(
        val title: String,
        val message: String,
        val priority: Priority,
        val type: NotificationType,
        val reminderDelayMs: Long? = null
    ) : NotificationIntent
}