package com.tejashree.codereviewlab.features.employeedirectory.data.repository

import com.tejashree.codereviewlab.features.employeedirectory.data.mapper.toDomain
import com.tejashree.codereviewlab.features.employeedirectory.data.service.EmployeesApiService
import com.tejashree.codereviewlab.features.employeedirectory.domain.model.Employee
import com.tejashree.codereviewlab.features.employeedirectory.domain.repository.EmployeesRepository
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

class EmployeesRepositoryImpl(
    private val api: EmployeesApiService
) : EmployeesRepository {
    override suspend fun fetchEmployees(resultType: EmployeeResult): Result<List<Employee>> =
        withContext(Dispatchers.IO) {
            try {
                val response = when (resultType) {
                    EmployeeResult.SUCCESS -> api.fetchEmployees()
                    EmployeeResult.EMPTY -> api.fetchEmployeesEmptyList()
                    EmployeeResult.ERROR -> api.fetchEmployeesMalformedList()
                }
                val employees = response.employees.map { dto ->
                    dto.toDomain()
                }
                Result.success(employees)
            } catch (e: Exception) {
                // Re-throw if the exception is due to Coroutine cancellation
                if (e is CancellationException) throw e
                Result.failure(e)
            }
        }
}