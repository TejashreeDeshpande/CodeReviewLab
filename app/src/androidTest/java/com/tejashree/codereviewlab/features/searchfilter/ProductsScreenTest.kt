package com.tejashree.codereviewlab.features.searchfilter

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme
import org.junit.Rule
import org.junit.Test

class ProductsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchLogic_isCaseInsensitiveAndMatchesPrefixes() {
        composeTestRule.setContent {
            CodeReviewLabTheme {
                ProductsScreen(onBack = {})
            }
        }

        // Initial state is loading, skip to success
        composeTestRule.onNodeWithContentDescription("Refresh").performClick() // index 1: Error
        composeTestRule.onNodeWithContentDescription("Refresh").performClick() // index 2: Success

        // Search for "wire" (prefix of "Wireless Headphones")
        composeTestRule.onNodeWithText("Search products").performTextInput("wire")

        // Should find "Wireless Headphones"
        composeTestRule.onNodeWithText("Wireless Headphones").assertIsDisplayed()
        
        // Clear and search for "HEAD" (case-insensitive)
        composeTestRule.onNodeWithText("wire").performTextReplacement("HEAD")
        composeTestRule.onNodeWithText("Wireless Headphones").assertIsDisplayed()
    }

    @Test
    fun filterToggling_appliesAndClearsFilter() {
        composeTestRule.setContent {
            CodeReviewLabTheme {
                ProductsScreen(onBack = {})
            }
        }

        // Initial state is loading, skip to success
        composeTestRule.onNodeWithContentDescription("Refresh").performClick() // index 1: Error
        composeTestRule.onNodeWithContentDescription("Refresh").performClick() // index 2: Success

        // Initially "Wireless Headphones" (Electronics) is visible
        composeTestRule.onNodeWithText("Wireless Headphones").assertIsDisplayed()

        // Click "Health" category
        composeTestRule.onNodeWithText(ProductCategory.HEALTH.title).performClick()

        // "Wireless Headphones" (Electronics) should disappear
        composeTestRule.onNodeWithText("Wireless Headphones").assertDoesNotExist()
        // "Smart Water Bottle" (Health) should be visible
        composeTestRule.onNodeWithText("Smart Water Bottle").assertIsDisplayed()

        // Click "Health" again to clear filter
        composeTestRule.onNodeWithText(ProductCategory.HEALTH.title).performClick()

        // "Wireless Headphones" should be back
        composeTestRule.onNodeWithText("Wireless Headphones").assertIsDisplayed()
    }

    @Test
    fun emptyResults_showsCorrectMessage() {
        val query = "NonExistentProduct"
        composeTestRule.setContent {
            CodeReviewLabTheme {
                ProductsScreen(onBack = {})
            }
        }

        // Initial state is loading, skip to success
        composeTestRule.onNodeWithContentDescription("Refresh").performClick() // index 1: Error
        composeTestRule.onNodeWithContentDescription("Refresh").performClick() // index 2: Success

        composeTestRule.onNodeWithText("Search products").performTextInput(query)

        composeTestRule.onNodeWithText("No product available for `$query`").assertIsDisplayed()
    }

    @Test
    fun uiStates_transitionsCorrectllyOnRefresh() {
        composeTestRule.setContent {
            CodeReviewLabTheme {
                ProductsScreen(onBack = {})
            }
        }

        // Initial state is Loading (index 0)
        composeTestRule.onNodeWithContentDescription("Loading products").assertIsDisplayed()

        // Click Refresh to go to Error state (index 1)
        composeTestRule.onNodeWithContentDescription("Refresh").performClick()
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()

        // Click Refresh to go to Success state (index 2)
        composeTestRule.onNodeWithContentDescription("Refresh").performClick()
        composeTestRule.onNodeWithText("Wireless Headphones").assertIsDisplayed()
        
        // Click Refresh again to go back to Loading state (index 0)
        composeTestRule.onNodeWithContentDescription("Refresh").performClick()
        composeTestRule.onNodeWithContentDescription("Loading products").assertIsDisplayed()
    }
}
