package com.tejashree.codereviewlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tejashree.codereviewlab.features.canvas.FluidCanvasScreen
import com.tejashree.codereviewlab.features.canvas.ProfessionalKaleidoscopeCanvas
import com.tejashree.codereviewlab.features.chat.data.ChatViewModel
import com.tejashree.codereviewlab.features.chat.presentation.ChatScreen
import com.tejashree.codereviewlab.features.counter.SimpleCounter
import com.tejashree.codereviewlab.features.dashboard.DashboardScreen
import com.tejashree.codereviewlab.features.dashboard.Feature
import com.tejashree.codereviewlab.features.employeedirectory.presentation.screens.EmployeeDirectory
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeesViewModel
import com.tejashree.codereviewlab.features.leaderboard.presentation.LeaderboardScreen
import com.tejashree.codereviewlab.features.leaderboard.presentation.LeaderboardViewModel
import com.tejashree.codereviewlab.features.mvi.notification.presentation.NotificationScreen
import com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel.NotificationViewModel
import com.tejashree.codereviewlab.features.mvvm.notes.navigation.NotesAppNavHost
import com.tejashree.codereviewlab.features.searchfilter.ProductsScreen
import com.tejashree.codereviewlab.features.vehicle.batterystatus.presentation.screens.BatteryStatusScreen
import com.tejashree.codereviewlab.features.vehicle.batterystatus.presentation.viewmodel.BatteryViewModel
import com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.screens.ParkAndGoScreen
import com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.viewmodel.ParkGoViewModel
import com.tejashree.codereviewlab.features.vehicle.smartparking.presentation.screens.SmartParkingHomeScreen
import com.tejashree.codereviewlab.features.vehicle.smartparking.presentation.viewmodel.SmartParkingViewModel
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodeReviewLabTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            DashboardScreen { feature ->
                navController.navigate(feature.name)
            }
        }

        composable(Feature.EmployeeDirectory.name) {
            val viewModel: EmployeesViewModel = koinViewModel()
            EmployeeDirectory(
                viewModel = viewModel,
                onBack = { navController.popBackStack() })
        }

        composable(Feature.Chat.name) {
            val viewModel: ChatViewModel = koinViewModel()
            ChatScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() })
        }

        composable(Feature.Kaleidoscope.name) {
            ProfessionalKaleidoscopeCanvas(onBack = { navController.popBackStack() })
        }

        composable(Feature.SmartParkingReminder.name) {
            val viewModel = koinViewModel<SmartParkingViewModel>()
            SmartParkingHomeScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Feature.ParkAndGo.name) {
            val viewModel = koinViewModel<ParkGoViewModel>()
            ParkAndGoScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Feature.BatteryStatus.name) {
            val viewModel = koinViewModel<BatteryViewModel>()
            BatteryStatusScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Feature.Notes.name) {
            NotesAppNavHost(onBack = { navController.popBackStack() })
        }

        composable(Feature.Notification.name) {
            val viewModel = koinViewModel<NotificationViewModel>()
            NotificationScreen(vm = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Feature.SearchFilter.name) {
            ProductsScreen(onBack = { navController.popBackStack() })
        }

        composable(Feature.Leaderboard.name) {
            val viewModel = koinViewModel<LeaderboardViewModel>()
            LeaderboardScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }

        composable(Feature.Counter.name) {
            SimpleCounter(onBack = { navController.popBackStack() })
        }

        composable(Feature.Galaxy.name) {
            FluidCanvasScreen(onBack = { navController.popBackStack() })
        }

        composable(Feature.Kaleidoscope.name) {
            ProfessionalKaleidoscopeCanvas(onBack = { navController.popBackStack() })
        }
    }
}
