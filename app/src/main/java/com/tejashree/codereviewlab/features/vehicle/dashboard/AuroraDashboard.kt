package com.tejashree.codereviewlab.features.vehicle.dashboard

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.BatteryChargingFull
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Navigation
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.Timeline
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.Wifi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF030B16)
private val SidebarBackground = Color(0xFF06111F)
private val CardBackground = Color(0xFF071421)
private val ElevatedCard = Color(0xFF0A1929)
private val BorderColor = Color(0xFF17324C)

private val Cyan = Color(0xFF24E6E6)
private val Blue = Color(0xFF448AFF)
private val Purple = Color(0xFF9B5CFF)
private val Green = Color(0xFF35E6A1)
private val Warning = Color(0xFFFFC857)
private val Danger = Color(0xFFFF5C7A)

private val PrimaryText = Color(0xFFEAF4FF)
private val SecondaryText = Color(0xFF93A8BF)
private val MutedText = Color(0xFF60758C)

private val AuroraColors = darkColorScheme(
    primary = Cyan,
    secondary = Purple,
    tertiary = Green,
    background = Background,
    surface = CardBackground,
    surfaceVariant = ElevatedCard,
    onPrimary = Background,
    onBackground = PrimaryText,
    onSurface = PrimaryText
)

@Composable
fun AuroraDashboardTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AuroraColors,
        content = content
    )
}

@Preview
@Composable
fun PreviewAuroraDashboardScreen() {
    AuroraDashboardScreen()
}

@Composable
fun AuroraDashboardScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF0A1B31),
                        Background
                    ),
                    radius = 1500f
                )
            )
            .padding(14.dp)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(26.dp),
            color = Color.Transparent,
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = BorderColor
            )
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val constraints = this

                val showSidebar = maxWidth >= 900.dp

                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (showSidebar) {
                        DashboardSidebar(
                            modifier = Modifier
                                .width(180.dp)
                                .fillMaxHeight()
                        )
                    }

                    DashboardContent(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        compact = constraints.maxWidth < 1100.dp
                    )
                }
            }
        }
    }
}

@Composable
private fun DashboardSidebar(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(SidebarBackground)
            .padding(horizontal = 14.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            BrandHeader()

            Spacer(modifier = Modifier.height(28.dp))

            SidebarItem(
                title = "Overview",
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Home,
                        contentDescription = null
                    )
                },
                selected = true
            )

            SidebarItem(
                title = "Perception",
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Visibility,
                        contentDescription = null
                    )
                }
            )

            SidebarItem(
                title = "Decisions",
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Psychology,
                        contentDescription = null
                    )
                }
            )

            SidebarItem(
                title = "Timeline",
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Timeline,
                        contentDescription = null
                    )
                }
            )

            SidebarItem(
                title = "Comfort",
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.AutoAwesome,
                        contentDescription = null
                    )
                }
            )

            SidebarItem(
                title = "Settings",
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Settings,
                        contentDescription = null
                    )
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PassengerCard()
            WeatherCard()
        }
    }
}

@Composable
private fun BrandHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    brush = Brush.linearGradient(
                        listOf(Blue, Purple, Cyan)
                    ),
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "A",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "AURORA",
                color = PrimaryText,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )

            Text(
                text = "AI ASSIST",
                color = SecondaryText,
                fontSize = 9.sp,
                letterSpacing = 2.sp
            )
        }
    }
}

@Composable
private fun SidebarItem(
    title: String,
    icon: @Composable () -> Unit,
    selected: Boolean = false
) {
    val background = if (selected) {
        Brush.horizontalGradient(
            listOf(
                Color(0xFF123153),
                Color(0xFF0C2036)
            )
        )
    } else {
        Brush.horizontalGradient(
            listOf(Color.Transparent, Color.Transparent)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                brush = background,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(25.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.runtime.CompositionLocalProvider(
                androidx.compose.material3.LocalContentColor provides
                        if (selected) Blue else SecondaryText
            ) {
                icon()
            }
        }

        Spacer(modifier = Modifier.width(13.dp))

        Text(
            text = title,
            color = if (selected) PrimaryText else SecondaryText,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
private fun PassengerCard() {
    DashboardCard(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 16.dp,
        contentPadding = 12.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = Color(0xFF17324C),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👩🏽",
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = "Ananya",
                    color = PrimaryText,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Passenger",
                    color = SecondaryText,
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Composable
private fun WeatherCard() {
    DashboardCard(
        modifier = Modifier.fillMaxWidth(),
        cornerRadius = 16.dp,
        contentPadding = 14.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "☀️",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = "23°C",
                    color = PrimaryText,
                    fontSize = 20.sp
                )

                Text(
                    text = "Outside",
                    color = SecondaryText,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    modifier: Modifier = Modifier,
    compact: Boolean
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        DashboardTopBar()

        if (compact) {
            SafetyBubbleCard()
            CurrentMissionCard()
            DecisionExplainerCard()
            DecisionTimelineCard()
            RideComfortCard()
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1.28f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    SafetyBubbleCard()
                    DecisionTimelineCard()
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    CurrentMissionCard()
                    DecisionExplainerCard()
                    RideComfortCard()
                }
            }
        }
    }
}

@Composable
private fun DashboardTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Good Morning, Ananya 👋",
            color = PrimaryText,
            fontSize = 23.sp,
            fontWeight = FontWeight.Medium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Wifi,
                contentDescription = null,
                tint = Green,
                modifier = Modifier.size(19.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Auto Drive",
                color = Green,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(22.dp))

            Text(
                text = "10:42 AM",
                color = SecondaryText,
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun SafetyBubbleCard() {
    DashboardCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SAFETY BUBBLE",
                    color = PrimaryText,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    tint = SecondaryText,
                    modifier = Modifier.size(17.dp)
                )
            }

            StatusBadge(
                text = "● Low",
                color = Green
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        SafetyBubbleVisualization(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            VehicleStatusCell(
                modifier = Modifier.weight(1f),
                emoji = "◉",
                title = "Sensors",
                value = "All Active"
            )

            VehicleStatusCell(
                modifier = Modifier.weight(1f),
                emoji = "◉",
                title = "Visibility",
                value = "Good"
            )

            VehicleStatusCell(
                modifier = Modifier.weight(1f),
                emoji = "🛣️",
                title = "Road",
                value = "Dry"
            )

            VehicleStatusCell(
                modifier = Modifier.weight(1f),
                emoji = "🚙",
                title = "Traffic",
                value = "Light"
            )
        }
    }
}

@Composable
private fun SafetyBubbleVisualization(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "safety-bubble"
    )

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.93f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(1700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bubble-pulse"
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF08172A),
                        Color(0xFF06111C)
                    )
                ),
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(18.dp)
            )
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val center = Offset(
                x = size.width * 0.50f,
                y = size.height * 0.69f
            )

            drawLine(
                color = Color(0xFF204C73),
                start = Offset(size.width * 0.30f, size.height),
                end = Offset(size.width * 0.43f, 0f),
                strokeWidth = 3f
            )

            drawLine(
                color = Color(0xFF204C73),
                start = Offset(size.width * 0.70f, size.height),
                end = Offset(size.width * 0.57f, 0f),
                strokeWidth = 3f
            )

            repeat(3) { index ->
                val radius = (65f + index * 45f) * pulse

                drawCircle(
                    color = Cyan.copy(alpha = 0.55f - index * 0.12f),
                    radius = radius,
                    center = center,
                    style = Stroke(width = 3f)
                )
            }

            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Cyan.copy(alpha = 0.22f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = 180f
                ),
                radius = 180f,
                center = center
            )

            drawLine(
                color = Color.White.copy(alpha = 0.55f),
                start = Offset(size.width * 0.48f, size.height * 0.12f),
                end = Offset(size.width * 0.46f, size.height * 0.30f),
                strokeWidth = 4f
            )

            drawLine(
                color = Color.White.copy(alpha = 0.55f),
                start = Offset(size.width * 0.52f, size.height * 0.12f),
                end = Offset(size.width * 0.54f, size.height * 0.30f),
                strokeWidth = 4f
            )
        }

        ObjectMarker(
            emoji = "🚘",
            label = "12 m",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 54.dp)
        )

        ObjectMarker(
            emoji = "🚙",
            label = "18 m",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 48.dp, bottom = 32.dp)
        )

        ObjectMarker(
            emoji = "🚗",
            label = "14 m",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 52.dp, bottom = 18.dp)
        )

        ObjectMarker(
            emoji = "🚴",
            label = "8 m",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp, bottom = 44.dp)
        )

        ObjectMarker(
            emoji = "🚶",
            label = "6 m",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 34.dp, bottom = 45.dp)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 63.dp)
                .size(width = 102.dp, height = 64.dp)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFF9FB6CA),
                            Color(0xFF26394B)
                        )
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
                .border(
                    width = 2.dp,
                    color = Cyan.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(22.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🚘",
                fontSize = 40.sp
            )
        }

        StatusBadge(
            text = "⬡ 3D View",
            color = PrimaryText,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}

@Composable
private fun ObjectMarker(
    emoji: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = emoji,
            fontSize = 32.sp
        )

        Surface(
            color = Color(0xD90A1624),
            shape = RoundedCornerShape(7.dp),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = BorderColor
            )
        ) {
            Text(
                text = label,
                modifier = Modifier.padding(
                    horizontal = 8.dp,
                    vertical = 3.dp
                ),
                color = PrimaryText,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun VehicleStatusCell(
    emoji: String,
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = ElevatedCard,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .background(
                    color = Green.copy(alpha = 0.12f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = emoji,
                color = Green,
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = title,
                color = SecondaryText,
                fontSize = 11.sp
            )

            Text(
                text = value,
                color = Green,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun CurrentMissionCard() {
    DashboardCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(
            title = "CURRENT MISSION",
            trailing = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = Blue,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Edit",
                        color = Blue,
                        fontSize = 12.sp
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        ElevatedCard,
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "🏢",
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Work",
                    color = PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Koramangala, Bengaluru",
                    color = SecondaryText,
                    fontSize = 12.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "18 min",
                    color = Cyan,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "ETA",
                    color = SecondaryText,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        RouteVisualization()

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MetricTile(
                label = "Distance",
                value = "12.4 km",
                modifier = Modifier.weight(1f)
            )

            MetricTile(
                label = "Arrival",
                value = "11:00 AM",
                modifier = Modifier.weight(1f)
            )

            MetricTile(
                label = "Battery",
                value = "78% 🔋",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun RouteVisualization() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .background(
                color = Color(0xFF07111D),
                shape = RoundedCornerShape(14.dp)
            )
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val y = size.height * 0.55f

            drawLine(
                brush = Brush.horizontalGradient(
                    colors = listOf(Green, Purple, Blue, Cyan)
                ),
                start = Offset(size.width * 0.08f, y),
                end = Offset(size.width * 0.90f, y - 12f),
                strokeWidth = 7f,
                cap = StrokeCap.Round
            )

            drawCircle(
                color = Green,
                radius = 8f,
                center = Offset(size.width * 0.08f, y)
            )

            drawCircle(
                color = Danger,
                radius = 8f,
                center = Offset(size.width * 0.90f, y - 12f)
            )
        }

        Text(
            text = "📍",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp),
            fontSize = 22.sp
        )

        Text(
            text = "🚙",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 70.dp, bottom = 16.dp),
            fontSize = 23.sp
        )

        Text(
            text = "📍",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp, bottom = 12.dp),
            fontSize = 22.sp
        )
    }
}

@Composable
private fun DecisionExplainerCard() {
    DashboardCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(
            title = "AI DECISION EXPLAINER",
            trailing = {
                StatusBadge(
                    text = "Live",
                    color = Green
                )
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Purple.copy(alpha = 0.16f),
                            ElevatedCard
                        )
                    ),
                    shape = RoundedCornerShape(14.dp)
                )
                .border(
                    width = 1.dp,
                    color = Purple.copy(alpha = 0.35f),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = Brush.radialGradient(
                            listOf(Purple, Color(0xFF3C1B70))
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Psychology,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(27.dp)
                )
            }

            Spacer(modifier = Modifier.width(13.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Slowing down",
                    color = PrimaryText,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Vehicle ahead is braking",
                    color = SecondaryText,
                    fontSize = 12.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Confidence",
                    color = Purple,
                    fontSize = 10.sp
                )

                Text(
                    text = "96%",
                    color = Purple,
                    fontSize = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Using",
            color = SecondaryText,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(9.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SensorEvidence(
                emoji = "📷",
                title = "Camera"
            )

            SensorEvidence(
                emoji = "📡",
                title = "Radar"
            )

            SensorEvidence(
                emoji = "〰️",
                title = "LiDAR"
            )

            SensorEvidence(
                emoji = "🗺️",
                title = "Map"
            )
        }
    }
}

@Composable
private fun SensorEvidence(
    emoji: String,
    title: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emoji,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = title,
            color = PrimaryText,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun DecisionTimelineCard() {
    DashboardCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(
            title = "DECISION TIMELINE"
        )

        Spacer(modifier = Modifier.height(20.dp))

        val events = listOf(
            TimelineEvent("10:40", "●", "Cruising", "Auto Drive", Green),
            TimelineEvent("10:41", "🚶", "Vehicle Ahead", "Detected", Purple),
            TimelineEvent("10:41", "〽", "Speed Reduced", "Smoothly", Blue),
            TimelineEvent("10:42", "⬡", "Maintaining", "Safe Distance", Cyan),
            TimelineEvent("10:42", "◉", "Monitoring", "Traffic Flow", SecondaryText)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            events.forEachIndexed { index, event ->
                TimelineEventNode(
                    event = event,
                    modifier = Modifier.weight(1f)
                )

                if (index < events.lastIndex) {
                    Text(
                        text = "→",
                        color = Blue,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 37.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = BorderColor
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "☝ Tap any event to see details",
            color = SecondaryText,
            fontSize = 11.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

private data class TimelineEvent(
    val time: String,
    val emoji: String,
    val title: String,
    val subtitle: String,
    val color: Color
)

@Composable
private fun TimelineEventNode(
    event: TimelineEvent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = event.time,
            color = SecondaryText,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    color = event.color.copy(alpha = 0.15f),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = event.color.copy(alpha = 0.55f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = event.emoji,
                color = event.color,
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = event.title,
            color = PrimaryText,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = event.subtitle,
            color = SecondaryText,
            fontSize = 9.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RideComfortCard() {
    DashboardCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        SectionHeader(
            title = "RIDE COMFORT"
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ComfortScore(
                modifier = Modifier.size(135.dp)
            )

            Spacer(modifier = Modifier.width(18.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(11.dp)
            ) {
                ComfortMetric(
                    title = "Smoothness",
                    value = "Excellent"
                )

                ComfortMetric(
                    title = "Braking",
                    value = "Gentle"
                )

                ComfortMetric(
                    title = "Acceleration",
                    value = "Smooth"
                )

                ComfortMetric(
                    title = "Noise Level",
                    value = "Low"
                )
            }
        }
    }
}

@Composable
private fun ComfortScore(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { 0.92f },
            modifier = Modifier.fillMaxSize(),
            color = Green,
            trackColor = Color(0xFF153025),
            strokeWidth = 8.dp
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "92",
                color = PrimaryText,
                fontSize = 42.sp,
                fontWeight = FontWeight.Light
            )

            Text(
                text = "Excellent",
                color = Green,
                fontSize = 11.sp
            )

            Text(
                text = "🌿",
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ComfortMetric(
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = SecondaryText,
            fontSize = 12.sp
        )

        Text(
            text = "$value  ●",
            color = Green,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun MetricTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFF06111D),
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = label,
            color = SecondaryText,
            fontSize = 10.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            color = PrimaryText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SectionHeader(
    title: String,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = PrimaryText,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 0.4.sp
        )

        trailing?.invoke()
    }
}

@Composable
private fun StatusBadge(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.10f),
        shape = RoundedCornerShape(9.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = color.copy(alpha = 0.22f)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 10.dp,
                vertical = 6.dp
            ),
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun DashboardCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 18.dp,
    contentPadding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF091827),
                        CardBackground
                    )
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
            .border(
                width = 1.dp,
                color = BorderColor,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(contentPadding),
        content = content
    )
}

@Preview(
    widthDp = 1500,
    heightDp = 900,
    showBackground = true
)
@Composable
private fun AuroraDashboardPreview() {
    AuroraDashboardTheme {
        AuroraDashboardScreen()
    }
}