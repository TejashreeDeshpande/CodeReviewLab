package com.tejashree.codereviewlab.features.vehicle.smartparking.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tejashree.codereviewlab.R
import com.tejashree.codereviewlab.features.vehicle.smartparking.presentation.viewmodel.SmartParkingViewModel
import org.koin.androidx.compose.koinViewModel

private val BluePrimary = Color(0xFF2563EB)
private val BlueLight = Color(0xFFDBEAFE)
private val Green = Color(0xFF10B981)
private val GreenLight = Color(0xFFD1FAE5)
private val Background = Color(0xFFF8FAFC)
private val Surface = Color.White
private val TextPrimary = Color(0xFF0F172A)
private val TextSecondary = Color(0xFF64748B)
private val SoftGray = Color(0xFFF1F5F9)

@Preview(showBackground = true)
@Composable
fun PreviewSmartParkingHomeScreen() {
    SmartParkingHomeScreenContent(
        remainingTime = "15",
        expirationTime = "Expires at 5:30 PM",
        onExtendClick = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartParkingHomeScreen(
    viewModel: SmartParkingViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val remainingTime by viewModel.remainingTime.collectAsState()
    val expirationTime by viewModel.expirationTime.collectAsState()

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Smart Parking Reminder",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        }
    ) { innerPadding ->
        SmartParkingHomeScreenContent(
            remainingTime = remainingTime,
            expirationTime = expirationTime,
            onExtendClick = { viewModel.extendParking() },
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Composable
fun SmartParkingHomeScreenContent(
    remainingTime: String,
    expirationTime: String,
    onExtendClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ParkingStatus()
        ParkingTimerCard(
            remainingTime = remainingTime,
            expirationTime = expirationTime,
            onExtendClick = onExtendClick
        )
        ParkingLocationCard()
        ParkingActionsRow()
    }
}

@Composable
fun ParkingStatus() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(GreenLight)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(Green)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Active Parking",
            color = Green,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ParkingTimerCard(
    remainingTime: String,
    expirationTime: String,
    onExtendClick: () -> Unit
) {
    ParkingCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = BluePrimary
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Parking Timer",
                    color = BluePrimary,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = remainingTime,
                            color = TextPrimary,
                            fontSize = 42.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "left",
                            color = TextSecondary,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = expirationTime,
                        color = TextSecondary
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.parking_car),
                    contentDescription = "Parked vehicle",
                    modifier = Modifier
                        .width(130.dp)
                        .height(90.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Button(
                onClick = onExtendClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePrimary
                )
            ) {
                Text(
                    text = "⏱️  Extend 15 min",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ParkingLocationCard() {
    ParkingCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = BluePrimary
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Parking Location",
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Accuracy: High",
                        color = TextSecondary,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Green,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            ParkingMapPlaceholder()

            ParkingAddressRow()

            ParkingRouteRow()
        }
    }
}

@Composable
fun ParkingMapPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFFE8EEF7))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val block = Color(0xFFDDE6F1)
            val road = Color(0xFFFFFFFF)
            val roadEdge = Color(0xFFD7DEE8)
            val park = Color(0xFFCDEFD8)
            val water = Color(0xFFBFDDF7)

            // City blocks
            repeat(4) { row ->
                repeat(5) { col ->
                    val left = (col * size.width / 5f) + 8.dp.toPx()
                    val top = (row * size.height / 4f) + 8.dp.toPx()
                    drawRoundRect(
                        color = block,
                        topLeft = Offset(left, top),
                        size = Size(
                            size.width / 5f - 16.dp.toPx(),
                            size.height / 4f - 16.dp.toPx()
                        ),
                        cornerRadius = CornerRadius(8.dp.toPx())
                    )
                }
            }

            // Parks
            drawRoundRect(
                color = park,
                topLeft = Offset(size.width * 0.05f, size.height * 0.08f),
                size = Size(size.width * 0.28f, size.height * 0.22f),
                cornerRadius = CornerRadius(14.dp.toPx())
            )

            drawRoundRect(
                color = park,
                topLeft = Offset(size.width * 0.67f, size.height * 0.68f),
                size = Size(size.width * 0.25f, size.height * 0.20f),
                cornerRadius = CornerRadius(14.dp.toPx())
            )

            // Water / creek
            drawLine(
                color = water,
                start = Offset(size.width * 0.88f, 0f),
                end = Offset(size.width * 0.70f, size.height),
                strokeWidth = 26.dp.toPx()
            )

            // Road edges
            fun roadLine(
                start: Offset,
                end: Offset,
                width: Float
            ) {
                drawLine(
                    color = roadEdge,
                    start = start,
                    end = end,
                    strokeWidth = width + 4.dp.toPx()
                )
                drawLine(
                    color = road,
                    start = start,
                    end = end,
                    strokeWidth = width
                )
            }

            // Main roads
            roadLine(
                Offset(0f, size.height * 0.32f),
                Offset(size.width, size.height * 0.24f),
                16.dp.toPx()
            )

            roadLine(
                Offset(0f, size.height * 0.72f),
                Offset(size.width, size.height * 0.58f),
                16.dp.toPx()
            )

            roadLine(
                Offset(size.width * 0.34f, 0f),
                Offset(size.width * 0.42f, size.height),
                16.dp.toPx()
            )

            roadLine(
                Offset(size.width * 0.65f, 0f),
                Offset(size.width * 0.56f, size.height),
                14.dp.toPx()
            )

            // Small roads
            roadLine(
                Offset(0f, size.height * 0.50f),
                Offset(size.width, size.height * 0.42f),
                8.dp.toPx()
            )

            roadLine(
                Offset(size.width * 0.12f, 0f),
                Offset(size.width * 0.22f, size.height),
                8.dp.toPx()
            )

            roadLine(
                Offset(size.width * 0.90f, 0f),
                Offset(size.width * 0.78f, size.height),
                8.dp.toPx()
            )

            val marker = Offset(size.width * 0.52f, size.height * 0.53f)

            // Walking route dotted line
            val start = Offset(size.width * 0.30f, size.height * 0.72f)
            val dotCount = 10
            for (i in 0..dotCount) {
                val t = i / dotCount.toFloat()
                val x = start.x + (marker.x - start.x) * t
                val y = start.y + (marker.y - start.y) * t
                drawCircle(
                    color = BluePrimary.copy(alpha = 0.65f),
                    radius = 2.4.dp.toPx(),
                    center = Offset(x, y)
                )
            }

            // Pulse
            drawCircle(
                color = BluePrimary.copy(alpha = 0.16f),
                radius = 24.dp.toPx(),
                center = marker
            )

            drawCircle(
                color = BluePrimary.copy(alpha = 0.28f),
                radius = 14.dp.toPx(),
                center = marker
            )

            drawCircle(
                color = BluePrimary,
                radius = 6.dp.toPx(),
                center = marker
            )

            drawCircle(
                color = Color.White,
                radius = 3.dp.toPx(),
                center = marker
            )
        }

        ParkingPin(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = (-18).dp)
        )

        Text(
            text = "Main St",
            color = Color(0xFF64748B),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 22.dp, top = 42.dp)
        )

        Text(
            text = "Fremont Downtown",
            color = Color(0xFF64748B),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 22.dp, bottom = 18.dp)
        )

        Text(
            text = "220m",
            color = BluePrimary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 18.dp, bottom = 18.dp)
        )
    }
}

@Composable
fun ParkingPin(modifier: Modifier) {
    Icon(
        imageVector = Icons.Default.LocationOn,
        contentDescription = null,
        tint = Color.Red,
        modifier = modifier.size(36.dp)
    )
}

@Composable
fun ParkingAddressRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(BlueLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Apartment,
                contentDescription = null,
                tint = BluePrimary
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Parked near: Fremont Downtown",
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Main St, Fremont, CA 94538, USA",
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = {},
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueLight,
                contentColor = BluePrimary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Navigation,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text("Directions")
        }
    }
}

@Composable
fun ParkingRouteRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(SoftGray)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.DirectionsWalk,
            contentDescription = null,
            tint = Green
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "3 min walk  •  220 m",
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Light traffic via Main St",
                color = TextSecondary,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Text(
            text = "View Route",
            color = BluePrimary,
            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = BluePrimary
        )
    }
}

@Composable
fun ParkingActionsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ParkingActionButton(
            text = "Save Parking",
            iconStr = "📍",
            modifier = Modifier.weight(1f),
            onClick = {}
        )

        ParkingActionButton(
            text = "End Parking",
            iconStr = "🛑",
            modifier = Modifier.weight(1f),
            onClick = {}
        )

        ParkingActionButton(
            text = "Remind Me",
            iconStr = "🔔",
            modifier = Modifier.weight(1f),
            onClick = {}
        )
    }
}

@Composable
fun ParkingActionButton(
    text: String,
    iconStr: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = iconStr, fontSize = 28.sp)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = text,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ParkingCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}