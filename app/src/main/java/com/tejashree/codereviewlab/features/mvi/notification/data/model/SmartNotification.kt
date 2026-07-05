package com.tejashree.codereviewlab.features.mvi.notification.data.model

/**
 * Represents a notification "note" in the Smart Inbox.
 * Note: These are currently in-memory mock notifications for UI demonstration
 * and are not integrated with the Android System Notification Service.
 */
data class SmartNotification(
    val id: Int,
    val title: String,
    val message: String,
    val priority: Priority,
    val type: NotificationType,
    val time: String,
    val createdAt: Long = System.currentTimeMillis(),
    val remindAt: Long? = null,
    val archived: Boolean = false,
    val snoozed: Boolean = false
)