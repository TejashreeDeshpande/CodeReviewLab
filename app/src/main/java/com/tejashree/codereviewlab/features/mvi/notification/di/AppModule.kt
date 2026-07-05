package com.tejashree.codereviewlab.features.mvi.notification.di

import com.tejashree.codereviewlab.features.mvi.notification.presentation.LocalNotificationHelper
import com.tejashree.codereviewlab.features.mvi.notification.presentation.viewmodel.NotificationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val notificationAppModule = module {
    singleOf(::LocalNotificationHelper)
    viewModelOf(::NotificationViewModel)
}