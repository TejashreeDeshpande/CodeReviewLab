package com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel

import androidx.compose.runtime.Immutable
import com.tejashree.codereviewlab.features.employeedirectory.domain.model.Employee
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface EmployeeUiState {
    data object Loading : EmployeeUiState
    data object EmptyState : EmployeeUiState

    data class ErrorState(val errorType: EmployeeErrorType) : EmployeeUiState

    data class Success(val data: ImmutableList<Employee>) : EmployeeUiState
}

enum class EmployeeErrorType {
    MALFORMED_DATA,
    SERVER_ERROR,
    NO_INTERNET,
    TIMEOUT,
    FORBIDDEN,
    UNKNOWN
}