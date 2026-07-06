package com.tejashree.codereviewlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tejashree.codereviewlab.features.mvi.notification.presentation.NotificationScreen
import com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel.NotificationViewModel
import com.tejashree.codereviewlab.features.mvvm.notes.navigation.NotesAppNavHost
import com.tejashree.codereviewlab.features.smartparking.presentation.screens.ParkingHomeScreen
import com.tejashree.codereviewlab.features.smartparking.presentation.viewmodel.ParkingViewModel
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

                val viewModel: ParkingViewModel = koinViewModel()
                ParkingHomeScreen(viewModel = viewModel)

//                NotesAppNavHost()
//                ProfessionalKaleidoscopeCanvas()
            }
        }
    }
}