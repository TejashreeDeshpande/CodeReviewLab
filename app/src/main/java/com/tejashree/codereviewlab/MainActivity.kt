package com.tejashree.codereviewlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tejashree.codereviewlab.features.leaderboard.presentation.LeaderboardScreen
import com.tejashree.codereviewlab.features.leaderboard.presentation.LeaderboardViewModel
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeReviewLabTheme {
//                val viewModel: NotificationViewModel = koinViewModel()
//                NotificationScreen(vm = viewModel)

//                val viewModel: ParkingViewModel = koinViewModel()
//                ParkingHomeScreen(viewModel = viewModel)

                val viewModel: LeaderboardViewModel = koinViewModel()
                LeaderboardScreen(viewModel = viewModel)

//                NotesAppNavHost()
//                ProfessionalKaleidoscopeCanvas()
            }
        }
    }
}