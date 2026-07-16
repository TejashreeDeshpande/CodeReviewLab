package com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel

import com.tejashree.codereviewlab.features.employeedirectory.data.mapper.InvalidEmployeeDataException
import com.tejashree.codereviewlab.features.employeedirectory.domain.model.MockEmployeeData
import com.tejashree.codereviewlab.features.employeedirectory.domain.repository.EmployeesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class EmployeesViewModelTest {

    private val repository: EmployeesRepository = mockk()
    private lateinit var viewModel: EmployeesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init calls loadEmployees and emits Success state when repository returns data`() = runTest {
        // Given
        val employees = MockEmployeeData.employeeList
        coEvery { repository.fetchEmployees(EmployeeResult.SUCCESS) } returns Result.success(employees)

        // When
        viewModel = EmployeesViewModel(repository)
        
        // Assert initial loading
        assertEquals(EmployeeUiState.Loading, viewModel.uiState.value)
        
        advanceUntilIdle()

        // Assert success
        val currentState = viewModel.uiState.value
        assertTrue(currentState is EmployeeUiState.Success)
        assertEquals(employees, (currentState as EmployeeUiState.Success).data)
    }

    @Test
    fun `loadEmployees with EMPTY resultType emits EmptyState`() = runTest {
        // Given: Initial load is SUCCESSful
        coEvery { repository.fetchEmployees(EmployeeResult.SUCCESS) } returns Result.success(MockEmployeeData.employeeList)
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        // Mocking the explicit EMPTY request
        coEvery { repository.fetchEmployees(EmployeeResult.EMPTY) } returns Result.success(emptyList())

        // When
        viewModel.loadEmployees(EmployeeResult.EMPTY)
        advanceUntilIdle()

        // Assert
        assertEquals(EmployeeUiState.EmptyState, viewModel.uiState.value)
    }

    @Test
    fun `loadEmployees with ERROR resultType emits MALFORMED_DATA error state`() = runTest {
        // Given: Initial load is SUCCESSful
        coEvery { repository.fetchEmployees(EmployeeResult.SUCCESS) } returns Result.success(MockEmployeeData.employeeList)
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        // Mocking the ERROR request returning a serialization failure
        coEvery { repository.fetchEmployees(EmployeeResult.ERROR) } returns
                Result.failure(kotlinx.serialization.SerializationException("Malformed JSON"))

        // When
        viewModel.loadEmployees(EmployeeResult.ERROR)
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is EmployeeUiState.ErrorState)
        assertEquals(EmployeeErrorType.MALFORMED_DATA, (currentState as EmployeeUiState.ErrorState).errorType)
    }


    @Test
    fun `init calls loadEmployees and emits EmptyState when repository returns empty list`() = runTest {
        // Given
        coEvery { repository.fetchEmployees(EmployeeResult.SUCCESS) } returns Result.success(emptyList())

        // When
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        // Assert empty state
        assertEquals(EmployeeUiState.EmptyState, viewModel.uiState.value)
    }

    @Test
    fun `loadEmployees emits NO_INTERNET error state when repository throws IOException`() = runTest {
        // Given
        coEvery { repository.fetchEmployees(any()) } throws IOException("No connection")

        // When
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is EmployeeUiState.ErrorState)
        assertEquals(EmployeeErrorType.NO_INTERNET, (currentState as EmployeeUiState.ErrorState).errorType)
    }

    @Test
    fun `loadEmployees emits SERVER_ERROR error state when repository returns IOException failure`() = runTest {
        // Given
        coEvery { repository.fetchEmployees(any()) } returns Result.failure(IOException("Server error"))

        // When
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is EmployeeUiState.ErrorState)
        assertEquals(EmployeeErrorType.SERVER_ERROR, (currentState as EmployeeUiState.ErrorState).errorType)
    }

    @Test
    fun `loadEmployees emits MALFORMED_DATA error state on InvalidEmployeeDataException`() = runTest {
        // Given
        coEvery { repository.fetchEmployees(any()) } returns Result.failure(InvalidEmployeeDataException("Bad data"))

        // When
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is EmployeeUiState.ErrorState)
        assertEquals(EmployeeErrorType.MALFORMED_DATA, (currentState as EmployeeUiState.ErrorState).errorType)
    }

    @Test
    fun `loadEmployees emits FORBIDDEN error state on 403 HttpException`() = runTest {
        // Given
        val response = Response.error<Unit>(403, okhttp3.ResponseBody.create(null, ""))
        val httpException = HttpException(response)
        coEvery { repository.fetchEmployees(any()) } returns Result.failure(httpException)

        // When
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is EmployeeUiState.ErrorState)
        assertEquals(EmployeeErrorType.FORBIDDEN, (currentState as EmployeeUiState.ErrorState).errorType)
    }

    @Test
    fun `loadEmployees with ERROR resultType emits correct error state`() = runTest {
        // Given
        coEvery { repository.fetchEmployees(EmployeeResult.SUCCESS) } returns Result.success(MockEmployeeData.employeeList)
        viewModel = EmployeesViewModel(repository)
        advanceUntilIdle()

        coEvery { repository.fetchEmployees(EmployeeResult.ERROR) } returns Result.failure(Exception("Generic error"))

        // When
        viewModel.loadEmployees(EmployeeResult.ERROR)
        
        // Assert loading state before idle
        runCurrent()
        assertEquals(EmployeeUiState.Loading, viewModel.uiState.value)
        
        advanceUntilIdle()

        // Assert unknown error state
        val currentState = viewModel.uiState.value
        assertTrue(currentState is EmployeeUiState.ErrorState)
        assertEquals(EmployeeErrorType.UNKNOWN, (currentState as EmployeeUiState.ErrorState).errorType)
    }
}
