package com.tejashree.codereviewlab.features.employeedirectory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeDTO(
    val uuid: String?,
    @SerialName("full_name")
    val fullName: String?,
    @SerialName("phone_number")
    val phoneNumber: String?,
    @SerialName("email_address")
    val emailAddress: String?,
    val biography: String?,
    @SerialName("photo_url_small")
    val photoUrlSmall: String?,
    @SerialName("photo_url_large")
    val photoUrlLarge: String?,
    val team: String?,
    @SerialName("employee_type")
    val employeeType: String?
)