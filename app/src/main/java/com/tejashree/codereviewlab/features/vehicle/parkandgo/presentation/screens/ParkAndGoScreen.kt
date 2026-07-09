package com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tejashree.codereviewlab.R
import com.tejashree.codereviewlab.features.vehicle.parkandgo.data.CheckItem
import com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.viewmodel.ParkGoUiState
import com.tejashree.codereviewlab.features.common.AppTopBar
import com.tejashree.codereviewlab.features.common.AppTopBarTheme
import com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.viewmodel.ParkGoViewModel
import com.tejashree.codereviewlab.ui.theme.*
import org.koin.androidx.compose.koinViewModel

@Composable
fun ParkAndGoScreen(
    viewModel: ParkGoViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ParkAndGoScreenContent(
        state = uiState,
        onToggleItem = viewModel::toggleItem,
        onAddItem = viewModel::addItem,
        onToggleAll = viewModel::toggleAll,
        onBack = onBack
    )
}

@Composable
fun ParkAndGoScreenContent(
    state: ParkGoUiState,
    onToggleItem: (Int) -> Unit,
    onAddItem: (String) -> Unit,
    onToggleAll: () -> Unit,
    onBack: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var newItemTitle by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                newItemTitle = ""
            },
            containerColor = ParkGoSurface,
            shape = RoundedCornerShape(28.dp),
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.AddCircleOutline,
                        contentDescription = stringResource(R.string.park_go_add_item_dialog_title),
                        tint = ParkGoAccent,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = stringResource(R.string.park_go_add_item_dialog_title),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = stringResource(R.string.park_go_add_item_dialog_subtitle),
                        color = ParkGoMutedText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = newItemTitle,
                        onValueChange = { newItemTitle = it },
                        placeholder = { Text(stringResource(R.string.park_go_add_item_dialog_placeholder), color = ParkGoPlaceholder) },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = ParkGoAccent,
                            unfocusedBorderColor = ParkGoOutline,
                            focusedLabelColor = ParkGoAccent,
                            cursorColor = ParkGoAccent
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newItemTitle.isNotBlank()) {
                            onAddItem(newItemTitle)
                            showDialog = false
                            newItemTitle = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = ParkGoAccent),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.park_go_add_item_dialog_confirm), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    newItemTitle = ""
                }) {
                    Text(stringResource(R.string.park_go_add_item_dialog_cancel), color = ParkGoMutedText)
                }
            }
        )
    }

    Scaffold(
        containerColor = ParkGoBackground,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.park_go_title),
                onBack = onBack,
                theme = AppTopBarTheme.DARK
            )
        },
        bottomBar = { ParkBottomBar() }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.park_go_subtitle),
                color = ParkGoMutedText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(20.dp))

            ParkMapCard(time = state.parkedAt)

            Spacer(Modifier.height(18.dp))

            ChecklistCard(
                items = state.items,
                onToggle = { item -> onToggleItem(item.id) },
                onAddItem = { showDialog = true },
                onToggleAll = onToggleAll,
                allChecked = state.allChecked
            )
        }
    }
}

@Composable
fun ParkMapCard(time: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(ParkGoSurface)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
            ) {
                RealisticMap(modifier = Modifier.fillMaxSize())

                Text(
                    text = stringResource(R.string.park_go_level_format, "2", "B", "27", time),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .background(ParkGoMapInfo.copy(alpha = 0.85f), RoundedCornerShape(18.dp))
                        .padding(16.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ParkGoMapInfo)
                    .padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🚶", fontSize = 28.sp)

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "San Francisco Center",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "865 Mission St, San Francisco",
                        color = ParkGoMutedText,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.park_go_walk_format, "5"),
                            color = ParkGoAccent,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = stringResource(R.string.park_go_dist_format, "0.3"),
                            color = ParkGoMutedText,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                OutlinedButton(
                    onClick = {},
                    border = BorderStroke(1.dp, ParkGoAccent),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(stringResource(R.string.park_go_directions), color = ParkGoAccent)
                }
            }
        }
    }
}

@Composable
fun RealisticMap(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.background(ParkGoMapBackground)) {
        val width = size.width
        val height = size.height

        // Draw grid/parking spots
        val spotWidth = 60f
        val spotHeight = 100f
        val spacing = 20f

        for (i in 0..10) {
            for (j in 0..5) {
                drawRect(
                    color = ParkGoMapGrid,
                    topLeft = Offset(i * (spotWidth + spacing) + 40f, j * (spotHeight + 40f) + 20f),
                    size = Size(spotWidth, spotHeight),
                    style = Stroke(width = 2f)
                )
            }
        }

        // Draw "Roads"
        val roadPath = Path().apply {
            moveTo(0f, height * 0.7f)
            lineTo(width, height * 0.7f)
            moveTo(width * 0.3f, 0f)
            lineTo(width * 0.3f, height)
        }

        drawPath(
            path = roadPath,
            color = ParkGoOutline,
            style = Stroke(width = 40f)
        )

        // Draw stylized car position
        val carPos = Offset(width * 0.55f, height * 0.45f)
        
        // Glow effect
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(ParkGoAccent.copy(alpha = 0.4f), Color.Transparent),
                center = carPos,
                radius = 80f
            ),
            radius = 80f,
            center = carPos
        )

        drawCircle(
            color = ParkGoAccent,
            radius = 12f,
            center = carPos
        )

        drawCircle(
            color = Color.White,
            radius = 6f,
            center = carPos
        )
    }
}

@Composable
fun ChecklistCard(
    items: List<CheckItem>,
    onToggle: (CheckItem) -> Unit,
    onAddItem: () -> Unit,
    onToggleAll: () -> Unit,
    allChecked: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(ParkGoSurface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.park_go_checklist_title),
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        text = stringResource(R.string.park_go_checklist_subtitle),
                        color = ParkGoMutedText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                TextButton(
                    onClick = onToggleAll,
                    colors = ButtonDefaults.textButtonColors(contentColor = ParkGoAccent)
                ) {
                    Text(
                        text = if (allChecked) stringResource(R.string.park_go_deselect_all) else stringResource(R.string.park_go_select_all),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            items.forEach { item ->
                ChecklistRow(
                    item = item,
                    onClick = { onToggle(item) }
                )

                Spacer(Modifier.height(10.dp))
            }

            OutlinedButton(
                onClick = onAddItem,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, ParkGoAccent)
            ) {
                Text(stringResource(R.string.park_go_add_custom), color = ParkGoAccent)
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (allChecked) ParkGoAccent else ParkGoAccentDisabled
                )
            ) {
                Text(
                    text = stringResource(R.string.park_go_ready),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun ChecklistRow(
    item: CheckItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(ParkGoSurfaceVariant)
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.icon,
            fontSize = 24.sp,
            modifier = Modifier
                .size(44.dp)
                .background(ParkGoOutline, CircleShape)
                .padding(8.dp)
        )

        Spacer(Modifier.width(14.dp))

        Text(
            text = item.title,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = item.checked,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = ParkGoAccent,
                uncheckedColor = ParkGoPlaceholder,
                checkmarkColor = Color.White
            )
        )
    }
}

@Composable
fun ParkBottomBar() {
    NavigationBar(
        containerColor = ParkGoBackground,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple(stringResource(R.string.park_go_nav_park), Icons.Rounded.LocationOn, true),
            Triple(stringResource(R.string.park_go_nav_history), Icons.Rounded.History, false),
            Triple(stringResource(R.string.park_go_nav_settings), Icons.Rounded.Settings, false)
        )

        items.forEach { (label, icon, isSelected) ->
            NavigationBarItem(
                selected = isSelected,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ParkGoAccent,
                    selectedTextColor = ParkGoAccent,
                    unselectedIconColor = ParkGoPlaceholder,
                    unselectedTextColor = ParkGoPlaceholder,
                    indicatorColor = ParkGoOutline.copy(alpha = 0.5f)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewParkAndGoScreen() {
    ParkAndGoScreenContent(
        state = ParkGoUiState(),
        onToggleItem = { _ -> },
        onAddItem = { _ -> },
        onToggleAll = {},
        onBack = {}
    )
}
