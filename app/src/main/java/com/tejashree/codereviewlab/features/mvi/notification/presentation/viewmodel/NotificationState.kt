package com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel

import androidx.compose.runtime.Immutable
import com.tejashree.codereviewlab.features.mvi.notification.data.model.Priority
import com.tejashree.codereviewlab.features.mvi.notification.data.model.SmartNotification
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class NotificationState(
    val notifications: ImmutableList<SmartNotification> = persistentListOf(),
    val selectedPriority: Priority? = null,
    val filter: NotificationFilter = NotificationFilter.ACTIVE
)