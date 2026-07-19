package com.tejashree.codereviewlab.features.employeedirectory.data.service

import com.tejashree.codereviewlab.features.employeedirectory.data.model.EmployeeListResponse
import retrofit2.http.GET

interface EmployeesApiService {
    @GET("sq-mobile-interview/employees.json")
    suspend fun fetchEmployees(): EmployeeListResponse

    @GET("sq-mobile-interview/employees_empty.json")
    suspend fun fetchEmployeesEmptyList(): EmployeeListResponse

    @GET("sq-mobile-interview/employees_malformed.json")
    suspend fun fetchEmployeesMalformedList(): EmployeeListResponse
}