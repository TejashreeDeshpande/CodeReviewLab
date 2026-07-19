package com.tejashree.codereviewlab.features.employeedirectory.data.model

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeListResponse(
    val employees: List<EmployeeDTO>
)