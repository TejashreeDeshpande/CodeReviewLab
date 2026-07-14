package com.tejashree.codereviewlab.features.employeedirectory.domain.repository

import com.tejashree.codereviewlab.features.employeedirectory.domain.model.Employee
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeResult

interface EmployeesRepository {
    suspend fun fetchEmployees(resultType: EmployeeResult = EmployeeResult.SUCCESS): Result<List<Employee>>
}