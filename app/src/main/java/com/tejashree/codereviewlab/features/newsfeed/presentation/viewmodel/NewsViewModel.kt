package com.tejashree.codereviewlab.features.newsfeed.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tejashree.codereviewlab.features.newsfeed.DataState
import com.tejashree.codereviewlab.features.newsfeed.NewsArticle
import com.tejashree.codereviewlab.features.newsfeed.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModel(
    private val repository: NewsRepository,
    networkMonitor: NetworkMonitor // Assumes wrapper over the Context flow from earlier
) : ViewModel() {

    // Automatically triggers a fresh fetch whenever network returns to Available
    val uiState: StateFlow<DataState<List<NewsArticle>>> = networkMonitor.connectionState
        .flatMapLatest { connection ->
            repository.getNewsFeed()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataState.Loading
        )

    val isOffline: StateFlow<Boolean> = networkMonitor.isOffline
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)
}