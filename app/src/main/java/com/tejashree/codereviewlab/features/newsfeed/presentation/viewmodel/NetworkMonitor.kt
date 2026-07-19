package com.tejashree.codereviewlab.features.newsfeed.presentation.viewmodel

import android.content.Context
import com.tejashree.codereviewlab.features.newsfeed.presentation.ConnectionState
import com.tejashree.codereviewlab.features.newsfeed.presentation.observeConnectivityAsFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface NetworkMonitor {
    val connectionState: Flow<ConnectionState>
    val isOffline: Flow<Boolean>
}

class LiveNetworkMonitor(context: Context) : NetworkMonitor {
    override val connectionState: Flow<ConnectionState> = context.observeConnectivityAsFlow()
    
    override val isOffline: Flow<Boolean> = connectionState
        .map { it is ConnectionState.Unavailable }
}
