package com.tejashree.codereviewlab.features.leaderboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.leaderboard.data.Player
import com.tejashree.codereviewlab.features.leaderboard.data.mockPlayers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LeaderboardViewModel : ViewModel() {

    private val _players = MutableStateFlow(mockPlayers.shuffled())
    val players: StateFlow<List<Player>> = _players

    val rankedPlayers: StateFlow<List<Player>> = _players
        .map { playerList ->
            val sortedList = playerList.sortedByDescending { it.score }

            var currentRank = 1

            sortedList.mapIndexed { index, player ->
                if (index > 0 && player.score != sortedList[index - 1].score) {
                    currentRank = index + 1 // Skips ranks based on the index position
                }
                player.copy(rank = currentRank)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}