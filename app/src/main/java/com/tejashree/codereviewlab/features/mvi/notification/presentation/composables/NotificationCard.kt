package com.tejashree.codereviewlab.features.mvi.notification.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tejashree.codereviewlab.features.mvi.notification.data.model.Priority
import com.tejashree.codereviewlab.features.mvi.notification.data.model.SmartNotification


import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import com.tejashree.codereviewlab.ui.theme.InboxError
import com.tejashree.codereviewlab.ui.theme.InboxSuccess
import com.tejashree.codereviewlab.ui.theme.InboxWarning

import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.isSystemInDarkTheme
import com.tejashree.codereviewlab.ui.theme.InboxErrorDark
import com.tejashree.codereviewlab.ui.theme.InboxSuccessDark
import com.tejashree.codereviewlab.ui.theme.InboxWarningDark

import androidx.compose.material3.OutlinedCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationCard(
    notification: SmartNotification,
    onArchive: () -> Unit,
    onSnooze: () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onArchive()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val color = when (dismissState.dismissDirection) {
                SwipeToDismissBoxValue.EndToStart -> MaterialTheme.colorScheme.errorContainer
                else -> Color.Transparent
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(color)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Archive,
                    contentDescription = "Archive",
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    ) {
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val priorityColor = when (notification.priority) {
                        Priority.HIGH -> if (isDark) InboxErrorDark else InboxError
                        Priority.MEDIUM -> if (isDark) InboxWarningDark else InboxWarning
                        Priority.LOW -> if (isDark) InboxSuccessDark else InboxSuccess
                    }
                    
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(priorityColor, CircleShape)
                    )
                    
                    Spacer(Modifier.width(12.dp))
                    
                    Text(
                        notification.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    
                    Text(
                        notification.time,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    notification.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.25f
                )

                Spacer(Modifier.height(16.dp))

                Row {
                    AssistChip(
                        onClick = onSnooze,
                        label = { Text("Snooze", style = MaterialTheme.typography.labelLarge) },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = MaterialTheme.colorScheme.primary,
                            leadingIconContentColor = MaterialTheme.colorScheme.primary,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                            disabledLeadingIconContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Schedule,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )

                    Spacer(Modifier.width(12.dp))

                    AssistChip(
                        onClick = onArchive,
                        label = { Text("Archive", style = MaterialTheme.typography.labelLarge) },
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = MaterialTheme.colorScheme.primary,
                            leadingIconContentColor = MaterialTheme.colorScheme.primary,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                            disabledLeadingIconContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        leadingIcon = {
                            Icon(
                                Icons.Default.Archive,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}
