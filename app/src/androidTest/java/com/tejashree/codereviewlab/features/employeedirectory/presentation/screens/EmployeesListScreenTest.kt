package com.tejashree.codereviewlab.features.employeedirectory.presentation.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.tejashree.codereviewlab.features.employeedirectory.domain.model.MockEmployeeData
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeErrorType
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeeUiState
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme
import kotlinx.collections.immutable.toImmutableList
import org.junit.Rule
import org.junit.Test

class EmployeesListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            CodeReviewLabTheme {
                EmployeeDirectoryContent(
                    state = EmployeeUiState.Loading,
                    onBack = {},
                    onErrorClick = {},
                    onEmptyClick = {},
                    onRefreshClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Loading employees").assertIsDisplayed()
    }

    @Test
    fun emptyState_showsEmptyMessage() {
        composeTestRule.setContent {
            CodeReviewLabTheme {
                EmployeeDirectoryContent(
                    state = EmployeeUiState.EmptyState,
                    onBack = {},
                    onErrorClick = {},
                    onEmptyClick = {},
                    onRefreshClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Employees List is Empty").assertIsDisplayed()
    }

    @Test
    fun errorState_showsCorrectErrorMessage() {
        composeTestRule.setContent {
            CodeReviewLabTheme {
                EmployeeDirectoryContent(
                    state = EmployeeUiState.ErrorState(EmployeeErrorType.NO_INTERNET),
                    onBack = {},
                    onErrorClick = {},
                    onEmptyClick = {},
                    onRefreshClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("No internet connection. Please check your network.").assertIsDisplayed()
    }

    @Test
    fun successState_displaysEmployeeList() {
        val employees = MockEmployeeData.employeeList.toImmutableList()
        composeTestRule.setContent {
            CodeReviewLabTheme {
                EmployeeDirectoryContent(
                    state = EmployeeUiState.Success(employees),
                    onBack = {},
                    onErrorClick = {},
                    onEmptyClick = {},
                    onRefreshClick = {}
                )
            }
        }

        // Check if employees are displayed
        employees.forEach { employee ->
            composeTestRule.onNodeWithText(employee.fullName).assertIsDisplayed()
            if (employee.team != null) {
                composeTestRule.onNodeWithText(employee.team).assertIsDisplayed()
            }
        }
    }

    @Test
    fun backButton_triggersCallback() {
        var backClicked = false
        composeTestRule.setContent {
            CodeReviewLabTheme {
                EmployeeDirectoryContent(
                    state = EmployeeUiState.Loading,
                    onBack = { backClicked = true },
                    onErrorClick = {},
                    onEmptyClick = {},
                    onRefreshClick = {}
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }

    @Test
    fun fabButtons_triggerCorrectCallbacks() {
        var errorClicked = false
        var emptyClicked = false
        var refreshClicked = false

        composeTestRule.setContent {
            CodeReviewLabTheme {
                EmployeeDirectoryContent(
                    state = EmployeeUiState.Loading,
                    onBack = {},
                    onErrorClick = { errorClicked = true },
                    onEmptyClick = { emptyClicked = true },
                    onRefreshClick = { refreshClicked = true }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Error").performClick()
        assert(errorClicked)

        composeTestRule.onNodeWithContentDescription("Empty List").performClick()
        assert(emptyClicked)

        composeTestRule.onNodeWithContentDescription("Refresh").performClick()
        assert(refreshClicked)
    }
}
