package com.tejashree.codereviewlab.features.employeedirectory.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Employee(
    val uuid: String,
    val fullName: String,
    val phoneNumber: String?,
    val emailAddress: String,
    val biography: String?,
    val photoUrlSmall: String?,
    val photoUrlLarge: String?,
    val team: String?,
    val employeeType: String
)