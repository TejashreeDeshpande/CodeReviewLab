package com.tejashree.codereviewlab.features.mvi.notification.presentation.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tejashree.codereviewlab.features.mvi.notification.data.model.Priority

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChipDefaults

@Composable
fun PriorityChips(
    selected: Priority?,
    onSelected: (Priority?) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            FilterChip(
                selected = selected == null,
                onClick = { onSelected(null) },
                label = {
                    Text(
                        "All Priorities",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledSelectedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            )
        }

        items(Priority.entries) { priority ->
            Spacer(Modifier.width(8.dp))

            FilterChip(
                selected = selected == priority,
                onClick = { onSelected(priority) },
                label = {
                    Text(
                        priority.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledSelectedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            )
        }
    }
}
