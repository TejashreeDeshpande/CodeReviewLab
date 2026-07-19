package com.tejashree.codereviewlab.features.employeedirectory.data.repository

import com.tejashree.codereviewlab.features.employeedirectory.data.mapper.InvalidEmployeeDataException
import com.tejashree.codereviewlab.features.employeedirectory.data.model.EmployeeDTO
import com.tejashree.codereviewlab.features.employeedirectory.data.model.EmployeeListResponse
import com.tejashree.codereviewlab.features.employeedirectory.data.service.EmployeesApiService
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class EmployeesRepositoryImplTest {

    private val api: EmployeesApiService = mockk()
    private val repository = EmployeesRepositoryImpl(api)

    @Test
    fun `fetchEmployees SUCCESS returns mapped employees`() = runTest {
        // Given
        val employeeDTO = EmployeeDTO(
            uuid = "123",
            fullName = "John Doe",
            phoneNumber = "1234567890",
            emailAddress = "john@example.com",
            biography = "Bio",
            photoUrlSmall = "small",
            photoUrlLarge = "large",
            team = "Team",
            employeeType = "FULL_TIME"
        )
        val response = EmployeeListResponse(employees = listOf(employeeDTO))
        coEvery { api.fetchEmployees() } returns response

        // When
        val result = repository.fetchEmployees(EmployeeResult.SUCCESS)

        // Then
        assertTrue(result.isSuccess)
        val employees = result.getOrNull()!!
        assertEquals(1, employees.size)
        assertEquals("John Doe", employees[0].fullName)
        assertEquals("123", employees[0].uuid)
    }

    @Test
    fun `fetchEmployees EMPTY returns empty list`() = runTest {
        // Given
        val response = EmployeeListResponse(employees = emptyList())
        coEvery { api.fetchEmployeesEmptyList() } returns response

        // When
        val result = repository.fetchEmployees(EmployeeResult.EMPTY)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull()?.size)
    }

    @Test
    fun `fetchEmployees ERROR returns failure`() = runTest {
        // Given
        val exception = IOException("Network Error")
        coEvery { api.fetchEmployeesMalformedList() } throws exception

        // When
        val result = repository.fetchEmployees(EmployeeResult.ERROR)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `fetchEmployees SUCCESS with api throwing exception returns failure`() = runTest {
        // Given
        val exception = IOException("Network Error")
        coEvery { api.fetchEmployees() } throws exception

        // When
        val result = repository.fetchEmployees(EmployeeResult.SUCCESS)

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `fetchEmployees SUCCESS with malformed data returns failure`() = runTest {
        // Given
        val employeeDTO = EmployeeDTO(
            uuid = null, // Missing mandatory field
            fullName = "John Doe",
            phoneNumber = "1234567890",
            emailAddress = "john@example.com",
            biography = "Bio",
            photoUrlSmall = "small",
            photoUrlLarge = "large",
            team = "Team",
            employeeType = "FULL_TIME"
        )
        val response = EmployeeListResponse(employees = listOf(employeeDTO))
        coEvery { api.fetchEmployees() } returns response

        // When
        val result = repository.fetchEmployees(EmployeeResult.SUCCESS)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is InvalidEmployeeDataException)
    }
}
