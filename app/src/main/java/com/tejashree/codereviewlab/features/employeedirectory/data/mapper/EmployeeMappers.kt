package com.tejashree.codereviewlab.features.employeedirectory.data.mapper

import com.tejashree.codereviewlab.features.employeedirectory.data.model.EmployeeDTO
import com.tejashree.codereviewlab.features.employeedirectory.domain.model.Employee

class InvalidEmployeeDataException(message: String) : Exception(message)

fun EmployeeDTO.toDomain(): Employee {
    return Employee(
        uuid = requireNotNull(uuid) { throw InvalidEmployeeDataException("Mandatory UUID is missing") },
        fullName = requireNotNull(fullName) { throw InvalidEmployeeDataException("Mandatory fullName is missing") },
        phoneNumber = requireNotNull(phoneNumber) { throw InvalidEmployeeDataException("Missing Phone Number is missing") },
        emailAddress = requireNotNull(emailAddress) { throw InvalidEmployeeDataException("Mandatory emailAddress is missing") },
        biography = requireNotNull(biography) { throw InvalidEmployeeDataException("Mandatory biography is missing") },
        photoUrlSmall = requireNotNull(photoUrlSmall) { throw InvalidEmployeeDataException("Mandatory small photo URL is missing") },
        photoUrlLarge = requireNotNull(photoUrlLarge) { throw InvalidEmployeeDataException("Mandatory Large Photo URL is missing") },
        team = requireNotNull(team) { throw InvalidEmployeeDataException("Mandatory team is missing") },
        employeeType = requireNotNull(employeeType) { throw InvalidEmployeeDataException("Mandatory Employee Type is missing") },
    )
}
