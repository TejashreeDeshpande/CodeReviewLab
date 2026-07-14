package com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.employeedirectory.data.mapper.InvalidEmployeeDataException
import com.tejashree.codereviewlab.features.employeedirectory.domain.repository.EmployeesRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlinx.collections.immutable.toImmutableList

class EmployeesViewModel(
    private val repository: EmployeesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<EmployeeUiState>(EmployeeUiState.Loading)
    val uiState: StateFlow<EmployeeUiState> = _uiState.asStateFlow()

    init {
        loadEmployees()
    }

    fun loadEmployees(resultType: EmployeeResult = EmployeeResult.SUCCESS) {
        viewModelScope.launch {
            _uiState.value = EmployeeUiState.Loading

            try {
                repository.fetchEmployees(resultType)
                    .onSuccess { employees ->
                        _uiState.value = if (employees.isEmpty()) {
                            EmployeeUiState.EmptyState
                        } else {
                            EmployeeUiState.Success(employees.toImmutableList())
                        }
                    }
                    .onFailure { exception ->
                        _uiState.value = when (exception) {
                            // 1. Data Validation / Parsing Failures -> ErrorState
                            is InvalidEmployeeDataException,
                            is kotlinx.serialization.SerializationException -> {
                                EmployeeUiState.ErrorState(EmployeeErrorType.MALFORMED_DATA)
                            }
                            // 2. HTTP Failures
                            is HttpException -> {
                                when (exception.code()) {
                                    403 -> EmployeeUiState.ErrorState(EmployeeErrorType.FORBIDDEN)
                                    else -> EmployeeUiState.ErrorState(EmployeeErrorType.SERVER_ERROR)
                                }
                            }
                            // 3. Structural Network/I/O Failures
                            is java.net.SocketTimeoutException -> EmployeeUiState.ErrorState(
                                EmployeeErrorType.TIMEOUT
                            )

                            is java.io.IOException -> EmployeeUiState.ErrorState(EmployeeErrorType.SERVER_ERROR)
                            // 4. Catch-all safety fallback -> ErrorState
                            else -> EmployeeUiState.ErrorState(EmployeeErrorType.UNKNOWN)
                        }
                    }
            } catch (e: Exception) {
                if (e is CancellationException) throw e

                _uiState.value = when (e) {
                    is java.net.UnknownHostException,
                    is java.io.IOException -> EmployeeUiState.ErrorState(EmployeeErrorType.NO_INTERNET)

                    else -> EmployeeUiState.ErrorState(EmployeeErrorType.UNKNOWN)
                }
            }
        }
    }
}

enum class EmployeeResult {
    EMPTY,
    ERROR,
    SUCCESS
}