package com.tejashree.codereviewlab.features.leaderboard.di

import com.tejashree.codereviewlab.features.leaderboard.presentation.LeaderboardViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val leaderboardModule = module {
    viewModelOf(::LeaderboardViewModel)
}
