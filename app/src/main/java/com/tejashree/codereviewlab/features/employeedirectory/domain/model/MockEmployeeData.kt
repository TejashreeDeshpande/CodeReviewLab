package com.tejashree.codereviewlab.features.employeedirectory.domain.model

object MockEmployeeData {

    val employeeList = listOf(
        Employee(
            uuid = "c0a80101-0000-0000-0000-000000000001",
            fullName = "Alex Morgan",
            phoneNumber = "555-019-2834",
            emailAddress = "alex.morgan@company.com",
            biography = "Senior Android Developer with a passion for clean architecture and smooth UI animations.",
            photoUrlSmall = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150",
            photoUrlLarge = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=500",
            team = "ENGINEER",
            employeeType = "FULL_TIME"
        ),
        Employee(
            uuid = "c0a80101-0000-0000-0000-000000000002",
            fullName = "Taylor Brooks",
            phoneNumber = null, // Testing null phone number UI presentation
            emailAddress = "taylor.b@company.com",
            biography = "Lead Product Designer managing the new multiplatform design system initiatives.",
            photoUrlSmall = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=150",
            photoUrlLarge = "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?w=500",
            team = "DESIGNER",
            employeeType = "FULL_TIME"
        ),
        Employee(
            uuid = "c0a80101-0000-0000-0000-000000000003",
            fullName = "Jordan Lee",
            phoneNumber = "555-014-9988",
            emailAddress = "jordan.lee@company.com",
            biography = null, // Testing null biography layout handling
            photoUrlSmall = null, // Testing default placeholder icon/avatar fallbacks
            photoUrlLarge = null,
            team = "PRODUCT",
            employeeType = "CONTRACTOR"
        ),
        Employee(
            uuid = "c0a80101-0000-0000-0000-000000000004",
            fullName = "Casey Vance",
            phoneNumber = "555-017-4411",
            emailAddress = "casey.vance@company.com",
            biography = "Growth Marketer specialized in performance metrics and user acquisition funnels.",
            photoUrlSmall = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=150",
            photoUrlLarge = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=500",
            team = "MARKETING",
            employeeType = "PART_TIME"
        )
    )
}