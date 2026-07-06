package com.tejashree.codereviewlab

import android.app.Application
import com.tejashree.codereviewlab.features.leaderboard.di.leaderboardModule
import com.tejashree.codereviewlab.features.mvi.notification.di.notificationAppModule
import com.tejashree.codereviewlab.features.mvvm.notes.di.notesAppModule
import com.tejashree.codereviewlab.features.vehicle.parkandgo.di.parkGoAppModule
import com.tejashree.codereviewlab.features.vehicle.smartparking.di.smartParkingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CodeReviewApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CodeReviewApp)

            modules(notesAppModule)
            modules(notificationAppModule)
            modules(smartParkingModule)
            modules(leaderboardModule)
            modules(parkGoAppModule)
        }
    }
}