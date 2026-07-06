package com.tejashree.codereviewlab.features.leaderboard.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tejashree.codereviewlab.features.leaderboard.data.Player
import com.tejashree.codereviewlab.features.leaderboard.data.mockPlayers
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun PreviewLeaderboardScreen() {
    LeaderboardScreen(onBack = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val players by viewModel.players.collectAsStateWithLifecycle()
    val rankedItems by viewModel.rankedPlayers.collectAsStateWithLifecycle()

    var showRank by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Leaderboard") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LeaderboardScreenContent(
            modifier = Modifier.padding(innerPadding),
            isFilterActive = showRank,
            players = if (showRank) rankedItems else players,
            onCheckedSwitch = { newValue ->
                showRank = newValue
            }
        )
    }
}

@Composable
fun LeaderboardScreenContent(
    isFilterActive: Boolean,
    players: List<Player>,
    onCheckedSwitch: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Show Rankings",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = isFilterActive,
                onCheckedChange = { newValue -> onCheckedSwitch(newValue) }
            )
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(players) { player ->

                ListItem(
                    headlineContent = { Text(text = player.name) },
                    supportingContent = { Text(text = player.score.toString()) },
                    trailingContent = {
                        if (isFilterActive) {
                            Text(text = "Rank: ${player.rank}")
                        }
                    }
                )

            }
        }
    }

}