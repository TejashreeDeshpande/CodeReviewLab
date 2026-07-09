package com.tejashree.codereviewlab.features.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tejashree.codereviewlab.features.common.AppTopBar

enum class Feature(val title: String, val description: String) {
    Counter("🔢 Simple Counter", "Basic State Management (rememberSavable)"),
    SearchFilter("🔍 Search & Filter", "Real-time filtering & Material 3 UI"),
    Leaderboard("🏆 Leaderboard", "List Performance & Animations"),
    SmartParkingReminder("🚗 Parking Reminder", "Custom Canvas Drawing & State Management"),
    ParkAndGo("🚗 Park & Go", "Custom Canvas Drawing & State Management"),
    BatteryStatus("🚗 Battery Status", "Custom Canvas Drawing & State Management"),
    Notes("📝 Notes App", "MVVM, Clean Architecture & Navigation 3"),
    Notification("🔔 Smart Notification", "MVI (Intent-State-Effect) Pattern"),
    Galaxy("🎨 Galaxy Canvas", "High-performance Fluid Graphics"),
    Kaleidoscope("🎨 Kaleidoscope", "Geometric Pathing & Animations")
}

@Composable
fun DashboardScreen(onFeatureClick: (Feature) -> Unit) {
    Scaffold(
        topBar = {
            AppTopBar(title = "Code Review Lab")
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(Feature.entries) { feature ->
                FeatureItem(feature = feature, onClick = { onFeatureClick(feature) })
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}

@Composable
fun FeatureItem(feature: Feature, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(feature.title) },
        supportingContent = { Text(feature.description) },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    )
}
