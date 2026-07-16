package com.tejashree.codereviewlab.features.employeedirectory.presentation.screens

import com.tejashree.codereviewlab.R
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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

    val currentOnBack by rememberUpdatedState(onBack)
    val onBackStable = remember { { currentOnBack() } }

    val onErrorClick = remember(viewModel) { { viewModel.loadEmployees(EmployeeResult.ERROR) } }
    val onEmptyClick = remember(viewModel) { { viewModel.loadEmployees(EmployeeResult.EMPTY) } }
    val onRefreshClick = remember(viewModel) { { viewModel.loadEmployees(EmployeeResult.SUCCESS) } }

    EmployeeDirectoryContent(
        state = state,
        onBack = onBackStable,
        onErrorClick = onErrorClick,
        onEmptyClick = onEmptyClick,
        onRefreshClick = onRefreshClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeDirectoryContent(
    state: EmployeeUiState,
    onBack: () -> Unit,
    onErrorClick: () -> Unit,
    onEmptyClick: () -> Unit,
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Employee Directory",
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
                FloatingActionButton(onClick = onErrorClick) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error"
                    )
                }
                FloatingActionButton(onClick = onEmptyClick) {
                    Icon(
                        imageVector = Icons.Default.Inbox,
                        contentDescription = "Empty List"
                    )
                }
                FloatingActionButton(onClick = onRefreshClick) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh"
                    )
                }
            }
        }) { innerPadding ->

        when (state) {
            is EmployeeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.semantics {
                            contentDescription = "Loading employees"
                        }
                    )
                }
            }

            is EmployeeUiState.EmptyState -> {
                EmployeeDirectoryEmpty(modifier = Modifier.padding(innerPadding))
            }

            is EmployeeUiState.ErrorState -> {
                val errorMessage = when (state.errorType) {
                    EmployeeErrorType.MALFORMED_DATA -> stringResource(R.string.malformed_data)
                    EmployeeErrorType.SERVER_ERROR -> stringResource(R.string.server_error)
                    EmployeeErrorType.NO_INTERNET -> stringResource(R.string.no_internet)
                    EmployeeErrorType.TIMEOUT -> stringResource(R.string.timeout)
                    EmployeeErrorType.FORBIDDEN -> stringResource(R.string.forbidden)
                    EmployeeErrorType.UNKNOWN -> stringResource(R.string.unknown)
                }
                EmployeeDirectoryError(
                    message = errorMessage,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is EmployeeUiState.Success -> {
                EmployeesDirectoryListView(
                    modifier = Modifier.padding(innerPadding),
                    employees = state.data
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