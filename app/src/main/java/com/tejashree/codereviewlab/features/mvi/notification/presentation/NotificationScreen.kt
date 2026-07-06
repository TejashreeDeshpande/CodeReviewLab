package com.tejashree.codereviewlab.features.mvi.notification.presentation

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tejashree.codereviewlab.features.mvi.notification.data.model.SmartNotification
import com.tejashree.codereviewlab.features.mvi.notification.presentation.composables.CreateNotificationSheet
import com.tejashree.codereviewlab.features.mvi.notification.presentation.composables.NotificationCard
import com.tejashree.codereviewlab.features.mvi.notification.presentation.composables.PriorityChips
import com.tejashree.codereviewlab.features.mvi.notification.presentation.composables.SummaryCard
import com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel.NotificationFilter
import com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel.NotificationIntent
import com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel.NotificationState
import com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel.NotificationViewModel
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    vm: NotificationViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val state by vm.state.collectAsStateWithLifecycle()
    val notifications by vm.filteredNotifications.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Request notification permissions for Android 13+
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ -> }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    NotificationScreenContent(
        state = state,
        notifications = notifications,
        scrollBehavior = scrollBehavior,
        onIntent = { vm.process(it) },
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreenContent(
    state: NotificationState,
    notifications: ImmutableList<SmartNotification>,
    scrollBehavior: TopAppBarScrollBehavior,
    onIntent: (NotificationIntent) -> Unit,
    onBack: () -> Unit
) {
    var showCreateSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Smart Inbox",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            "Productivity Assistant",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                ),
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateSheet = true },
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary,
                shape = MaterialTheme.shapes.extraLarge,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(Modifier.width(2.dp))
                    Text("New", fontWeight = FontWeight.Bold)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                SummaryCard(state)
            }

            item {
                SecondaryTabRow(
                    selectedTabIndex = state.filter.ordinal,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.tertiary,
                    divider = {}
                ) {
                    NotificationFilter.entries.forEach { filter ->
                        Tab(
                            selected = state.filter == filter,
                            onClick = {
                                onIntent(NotificationIntent.SetFilter(filter))
                            },
                            text = {
                                Text(
                                    filter.name.lowercase().replaceFirstChar { it.uppercase() },
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        )
                    }
                }
            }

            item {
                PriorityChips(
                    selected = state.selectedPriority,
                    onSelected = {
                        onIntent(NotificationIntent.Filter(it))
                    }
                )
            }

            items(
                notifications,
                key = { it.id }
            ) { notification ->
                NotificationCard(
                    notification = notification,
                    onArchive = {
                        onIntent(NotificationIntent.Archive(notification.id))
                    },
                    onSnooze = {
                        onIntent(NotificationIntent.Snooze(notification.id))
                    }
                )
            }
        }
        if (showCreateSheet) {
            CreateNotificationSheet(
                onDismiss = { showCreateSheet = false },
                onCreate = { title, message, priority, type, delay ->
                    onIntent(
                        NotificationIntent.CreateNotification(
                            title = title,
                            message = message,
                            priority = priority,
                            type = type,
                            reminderDelayMs = delay
                        )
                    )
                    showCreateSheet = false
                }
            )
        }
    }
}
