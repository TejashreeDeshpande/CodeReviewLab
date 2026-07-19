package com.tejashree.codereviewlab.features.newsfeed.presentation.screens


import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tejashree.codereviewlab.features.newsfeed.DataState
import com.tejashree.codereviewlab.features.newsfeed.NewsArticle
import com.tejashree.codereviewlab.features.newsfeed.presentation.viewmodel.NewsViewModel

@Composable
fun NewsFeed(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = viewModel() // Provide or inject your ViewModel here
) {
    val uiState by viewModel.uiState.collectAsState()
    val isOffline by viewModel.isOffline.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. Connectivity Banner at the very top
            OfflineStatusBanner(isOffline = isOffline)

            // 2. Primary Content Area
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(targetState = uiState, label = "NewsFeedState") { state ->
                    when (state) {
                        is DataState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is DataState.Error -> {
                            Text(
                                text = "Unable to load feed. Check your connection.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                        is DataState.Success -> {
                            // Dim the list content slightly if it's stale/cached data
                            val contentAlpha = if (state.isStale) 0.75f else 1.0f

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .alpha(contentAlpha)
                            ) {
                                // 3. Sub-banner inline notice for cached/stale mode
                                if (state.isStale) {
                                    item {
                                        Surface(
                                            color = MaterialTheme.colorScheme.surfaceVariant,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "You are viewing offline cached content.",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                modifier = Modifier.padding(vertical = 6.dp, horizontal = 16.dp)
                                            )
                                        }
                                    }
                                }

                                items(
                                    items = state.data,
                                    key = { article -> article.id }
                                ) { article ->
                                    // Replace this with your custom row item component
                                    Text(
                                        text = article.title,
                                        modifier = Modifier.padding(16.dp),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}