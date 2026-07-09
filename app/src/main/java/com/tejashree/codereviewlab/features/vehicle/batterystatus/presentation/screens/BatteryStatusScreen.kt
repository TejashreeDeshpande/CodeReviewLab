package com.tejashree.codereviewlab.features.vehicle.batterystatus.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.EvStation
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tejashree.codereviewlab.R
import com.tejashree.codereviewlab.features.common.AppTopBar
import com.tejashree.codereviewlab.features.common.AppTopBarTheme
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.BatteryTelemetry
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.model.ChargingStatus
import com.tejashree.codereviewlab.features.vehicle.batterystatus.presentation.viewmodel.BatteryViewModel
import com.tejashree.codereviewlab.ui.theme.BatteryAccent
import com.tejashree.codereviewlab.ui.theme.BatteryBackground
import com.tejashree.codereviewlab.ui.theme.BatteryHighlight
import com.tejashree.codereviewlab.ui.theme.BatterySurface
import com.tejashree.codereviewlab.ui.theme.BatteryTextSecondary
import com.tejashree.codereviewlab.ui.theme.BatteryTextTertiary
import org.koin.androidx.compose.koinViewModel

/**
 * Main screen for displaying the battery status and charging telemetry of the vehicle.
 */
@Composable
fun BatteryStatusScreen(
    viewModel: BatteryViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = BatteryBackground,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.battery_title),
                onBack = onBack,
                theme = AppTopBarTheme.DARK
            )
        },
        bottomBar = {
            BatteryBottomBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = BatteryAccent)
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.error ?: "Unknown error",
                            color = Color.White
                        )
                    }
                }

                uiState.telemetry != null -> {
                    BatteryContent(
                        telemetry = uiState.telemetry!!,
                        onRefresh = viewModel::refresh
                    )
                }
            }
        }
    }
}

/**
 * Scrollable content area for the battery status.
 */
@Composable
fun BatteryContent(
    telemetry: BatteryTelemetry,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection(telemetry, onRefresh)

        BatteryDisplay(telemetry)

        InfoCard(
            title = stringResource(R.string.battery_estimated_range),
            value = "${telemetry.estimatedRangeMiles} ${stringResource(R.string.battery_unit_miles)}",
            icon = Icons.Rounded.Bolt
        )

        InfoCard(
            title = stringResource(R.string.battery_charging),
            value = stringResource(R.string.battery_charging_format, telemetry.chargingPowerKw),
            icon = Icons.Rounded.Bolt
        )

        InfoCard(
            title = stringResource(R.string.battery_time_to_full),
            value = telemetry.timeToFull,
            icon = Icons.Rounded.Bolt
        )

        Spacer(Modifier.height(24.dp))
    }
}

/**
 * Large display showing the battery percentage and a vehicle illustration.
 */
@Composable
private fun BatteryDisplay(telemetry: BatteryTelemetry) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "${telemetry.batteryPercent}",
                color = Color.White,
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 84.sp,
                    fontWeight = FontWeight.W300
                )
            )
            Text(
                text = stringResource(R.string.battery_unit_percent),
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 16.dp, start = 4.dp)
            )
        }

        Text(
            text = stringResource(R.string.battery_state_of_charge),
            color = BatteryTextTertiary,
            style = MaterialTheme.typography.bodyLarge
        )

        // Car Illustration Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.vehicle_image),
                contentDescription = "Car",
                modifier = Modifier.fillMaxWidth(0.9f),
                contentScale = ContentScale.Fit
            )

            // Battery overlay effect (matching mockup green segments)
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 10.dp) // Adjust based on car image
                    .width(80.dp)
                    .height(20.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(BatteryAccent.copy(alpha = 0.6f))
                    )
                }
            }
        }
    }
}

/**
 * Reusable card component for displaying telemetry metrics.
 */
@Composable
private fun InfoCard(
    title: String,
    value: String,
    icon: ImageVector,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BatterySurface
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BatteryAccent,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(16.dp))


            Column {
                Text(
                    text = title,
                    color = BatteryTextSecondary,
                    style = MaterialTheme.typography.labelLarge
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = value,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
            }
        }
    }
}

/**
 * Top section showing the "Live" status and the last update timestamp.
 */
@Composable
private fun HeaderSection(
    telemetry: BatteryTelemetry,
    onRefresh: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(BatteryAccent, CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = stringResource(R.string.battery_live),
                        color = BatteryAccent,
                        fontWeight = FontWeight.Bold
                    )
                    Canvas(
                        modifier = Modifier
                            .width(24.dp)
                            .height(4.dp)
                    ) {
                        val path = Path().apply {
                            moveTo(0f, 0f)
                            quadraticTo(size.width / 2, size.height, size.width, 0f)
                        }
                        drawPath(
                            path = path,
                            color = BatteryAccent,
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = telemetry.updatedAgo,
                    color = BatteryTextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )

                IconButton(onClick = onRefresh) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = stringResource(R.string.battery_refresh),
                        tint = Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

/**
 * Custom Bottom Navigation Bar for the Battery feature.
 */
@Composable
fun BatteryBottomBar() {
    NavigationBar(
        containerColor = BatteryBackground,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            Triple(stringResource(R.string.battery_overview), Icons.Rounded.Home, true),
            Triple(stringResource(R.string.battery_charge), Icons.Rounded.EvStation, false),
            Triple(stringResource(R.string.battery_climate), Icons.Rounded.Thermostat, false),
            Triple(stringResource(R.string.battery_more), Icons.Rounded.MoreHoriz, false)
        )

        items.forEach { (label, icon, isSelected) ->
            NavigationBarItem(
                selected = isSelected,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BatteryHighlight,
                    selectedTextColor = BatteryHighlight,
                    unselectedIconColor = BatteryTextSecondary,
                    unselectedTextColor = BatteryTextSecondary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BatteryStatusScreenPreview() {
    val mockTelemetry = BatteryTelemetry(
        batteryPercent = 85,
        estimatedRangeMiles = 240,
        chargingStatus = ChargingStatus.DISCHARGING,
        chargingPowerKw = 0.0,
        timeToFull = "45 min",
        updatedAgo = "2 min ago",
        isFresh = true
    )
    
    MaterialTheme {
        Scaffold(
            containerColor = BatteryBackground,
            bottomBar = { BatteryBottomBar() }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp)
            ) {
                BatteryContent(
                    telemetry = mockTelemetry,
                    onRefresh = {}
                )
            }
        }
    }
}
