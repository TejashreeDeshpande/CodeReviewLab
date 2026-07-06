package com.tejashree.codereviewlab.features.searchfilter

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tejashree.codereviewlab.features.common.AppTopBar
import com.tejashree.codereviewlab.ui.theme.CodeReviewLabTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewProductsLoadingState() {
    CodeReviewLabTheme {
        ProductsScreenContent(state = ProductsScreenUiState.Loading)
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewProductsErrorState() {
    CodeReviewLabTheme {
        ProductsScreenContent(state = ProductsScreenUiState.Error("something went wrong"))
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewProductsState() {
    CodeReviewLabTheme {
        ProductsScreenContent(state = ProductsScreenUiState.Success(mockProducts))
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PreviewProductsScreen() {
    CodeReviewLabTheme {
        ProductsScreen(onBack = {})
    }
}

private fun filterProducts(
    products: List<Product>,
    query: String,
    category: ProductCategory?
): ImmutableList<Product> {
    return products.filter { product ->
        val matchesQuery = query.isBlank() ||
                product.name.split("\\s+".toRegex())
                    .any { word ->
                        word.startsWith(query, ignoreCase = true)
                    }

        val matchesFilter = category == null || product.category == category

        matchesQuery && matchesFilter
    }.toImmutableList()
}

@Composable
fun ProductsScreen(onBack: () -> Unit) {

    var successState by remember { mutableStateOf(ProductsScreenUiState.Success(mockProducts)) }
    var index by rememberSaveable { mutableIntStateOf(0) }

    val uiState = when (index) {
        0 -> ProductsScreenUiState.Loading
        1 -> ProductsScreenUiState.Error("Something went wrong")
        else -> successState
    }

    val rotation by animateFloatAsState(
        targetValue = if (index % 2 == 0) 360f else 0f,
        label = "rotation"
    )
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Search & Filter",
                onBack = onBack
            )
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = {
                    index = (index + 1) % 3

                    // Reset Query, filter
                    successState = ProductsScreenUiState.Success(mockProducts)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    modifier = Modifier.rotate(rotation)
                )
            }
        }
    ) { innerPadding ->
        ProductsScreenContent(
            state = uiState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            onQueryChange = { newQuery ->
                successState = successState.copy(
                    query = newQuery,
                    products = filterProducts(mockProducts, newQuery, successState.filter)
                )
            },
            onFilterChange = { clickedCategory ->
                // Toggle logic: If they click the already selected category, clear it to null ("All")
                val newFilter =
                    if (successState.filter == clickedCategory) null else clickedCategory

                successState = successState.copy(
                    filter = newFilter,
                    products = filterProducts(mockProducts, successState.query, newFilter)
                )
            },
        )
    }
}

@Composable
fun ProductsScreenContent(
    state: ProductsScreenUiState,
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {},
    onFilterChange: (ProductCategory) -> Unit = {},
) {
    when (state) {
        ProductsScreenUiState.Loading -> {
            ProductLoadingState(modifier = modifier)
        }

        is ProductsScreenUiState.Error -> {
            ProductsErrorState(state.message, modifier = modifier)
        }

        is ProductsScreenUiState.Success -> {
            ProductsListScreen(
                state.products,
                query = state.query,
                onQueryChange = onQueryChange,
                filter = state.filter,
                onFilterChange = onFilterChange,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ProductsListScreen(
    products: ImmutableList<Product>,
    query: String,
    onQueryChange: (String) -> Unit,
    filter: ProductCategory?,
    onFilterChange: (ProductCategory) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = { newValue -> onQueryChange(newValue) },
            label = { Text("Search products") },
            singleLine = true
        )
        FlowRow {
            ProductCategory.entries.forEach { category ->
                FilterChip(
                    selected = filter == category,
                    onClick = { onFilterChange(category) },
                    label = { Text(text = category.title) })
            }
        }
        if (products.isEmpty()) {
            Text(
                "No product available for `$query`",
                modifier = Modifier.semantics {
                    liveRegion = LiveRegionMode.Polite
                },
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(
                modifier = Modifier.semantics {
                    contentDescription = "Sharing ${products.size} products"
                }
            ) {
                items(
                    products,
                    key = { it.id }
                ) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .semantics(mergeDescendants = true) {
                                contentDescription =
                                    "${item.name}, ${item.category.title}. ${item.desc}"
                            }
                    ) {
                        ListItem(
                            headlineContent = {
                                Text(item.name)
                            },
                            supportingContent = {
                                Text(item.desc, style = MaterialTheme.typography.bodySmall)
                            },
                            trailingContent = {
                                Text(
                                    text = item.category.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductsErrorState(
    error: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = error,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun ProductLoadingState(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics {
                contentDescription = "Loading products"
            }
        )
    }
}