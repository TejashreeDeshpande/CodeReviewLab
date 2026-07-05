package com.tejashree.codereviewlab.features.mvi.notification.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tejashree.codereviewlab.features.mvi.notification.data.model.NotificationType
import com.tejashree.codereviewlab.features.mvi.notification.data.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNotificationSheet(
    onDismiss: () -> Unit,
    onCreate: (
        title: String,
        message: String,
        priority: Priority,
        type: NotificationType,
        reminderDelayMs: Long?
    ) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var type by remember { mutableStateOf(NotificationType.WORK) }
    var reminderDelayMs by remember { mutableStateOf<Long?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Create New Notification",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )

            Text("Priority", fontWeight = FontWeight.SemiBold)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Priority.entries.forEach {
                    FilterChip(
                        selected = priority == it,
                        onClick = { priority = it },
                        label = { Text(it.name) }
                    )
                }
            }

            Text("Reminder", fontWeight = FontWeight.SemiBold)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(
                    "Now" to null,
                    "1 min" to 60000L,
                    "5 min" to 300000L
                ).forEach { (label, delay) ->
                    FilterChip(
                        selected = reminderDelayMs == delay,
                        onClick = { reminderDelayMs = delay },
                        label = { Text(label) }
                    )
                }
            }

            Text("Category", fontWeight = FontWeight.SemiBold)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                NotificationType.entries.forEach {
                    FilterChip(
                        selected = type == it,
                        onClick = { type = it },
                        label = { Text(it.name) }
                    )
                }
            }

            Button(
                onClick = {
                    if (title.isNotBlank() && message.isNotBlank()) {
                        onCreate(title, message, priority, type, reminderDelayMs)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (reminderDelayMs == null) "Create Now" else "Schedule Reminder")
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}
