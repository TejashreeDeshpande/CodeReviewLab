package com.tejashree.codereviewlab.features.searchfilter

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface ProductsScreenUiState {
    data class Success(
        val products: ImmutableList<Product>,
        val query: String = "",
        val filter: ProductCategory? = null,
    ) : ProductsScreenUiState

    data class Error(val message: String) : ProductsScreenUiState
    data object Loading : ProductsScreenUiState
}