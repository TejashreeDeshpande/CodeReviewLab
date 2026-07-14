package com.tejashree.codereviewlab.features.employeedirectory.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tejashree.codereviewlab.features.employeedirectory.domain.model.Employee
import com.tejashree.codereviewlab.features.employeedirectory.domain.model.MockEmployeeData
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeErrorType
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeResult
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeUiState
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeesViewModel
import com.tejashree.codereviewlab.ui.theme.Background
import com.tejashree.codereviewlab.ui.theme.TextPrimary
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun PreviewEmployeeDirectoryEmptyScreen() {
    EmployeeDirectoryEmpty()
}

@Preview(showBackground = true)
@Composable
fun PreviewEmployeeDirectoryErrorScreen() {
    EmployeeDirectoryError(message = "Network Error")
}

@Preview(showBackground = true)
@Composable
fun PreviewEmployeeDirectoryListScreen() {
    EmployeesDirectoryListView(
        employees = MockEmployeeData.employeeList.toImmutableList()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDirectory(
    viewModel: EmployeesViewModel = koinViewModel(),
    onBack: () -> Unit
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background
                )
            )
        },
        floatingActionButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                FloatingActionButton(onClick = {
                    viewModel.loadEmployees(EmployeeResult.ERROR)
                }) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error"
                    )
                }
                FloatingActionButton(onClick = {
                    viewModel.loadEmployees(EmployeeResult.EMPTY)
                }) {
                    Icon(
                        imageVector = Icons.Default.Inbox,
                        contentDescription = "Empty List"
                    )
                }
                FloatingActionButton(onClick = {
                    viewModel.loadEmployees(EmployeeResult.SUCCESS)
                }) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        }) { innerPadding ->

        when (val currentState = state) {
            is EmployeeUiState.Loading -> {
                CircularProgressIndicator()
            }

            is EmployeeUiState.EmptyState -> {
                EmployeeDirectoryEmpty()
            }

            is EmployeeUiState.ErrorState -> {
                val errorMessage = when (currentState.errorType) {
                    EmployeeErrorType.MALFORMED_DATA -> "The data from the server was invalid."
                    EmployeeErrorType.SERVER_ERROR -> "There was an error on the server. Please try again later."
                    EmployeeErrorType.NO_INTERNET -> "No internet connection. Please check your network."
                    EmployeeErrorType.TIMEOUT -> "The request timed out. Please try again."
                    EmployeeErrorType.FORBIDDEN -> "You do not have permission to access this data."
                    EmployeeErrorType.UNKNOWN -> "An unknown error occurred."
                }
                EmployeeDirectoryError(message = errorMessage)
            }

            is EmployeeUiState.Success -> {
                EmployeesDirectoryListView(
                    modifier = Modifier.padding(innerPadding),
                    employees = currentState.data
                )
            }
        }
    }
}

@Composable
fun EmployeeDirectoryEmpty(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Employees List is Empty")
    }
}

@Composable
fun EmployeeDirectoryError(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
    }
}

@Composable
fun EmployeesDirectoryListView(
    employees: ImmutableList<Employee>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(
            items = employees,
            key = { employee -> employee.uuid }) { employee ->
            ListItem(
                headlineContent = { Text(employee.fullName) },
                supportingContent = { if (employee.team != null) Text(employee.team) },
                leadingContent = {
                    if (employee.photoUrlSmall != null) {
                        AsyncImage(
                            model = employee.photoUrlSmall,
                            contentDescription = "Profile picture of ${employee.fullName}",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Default avatar placeholder",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                }
            )
        }
    }
}