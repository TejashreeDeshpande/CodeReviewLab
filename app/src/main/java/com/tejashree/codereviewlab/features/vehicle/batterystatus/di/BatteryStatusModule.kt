package com.tejashree.codereviewlab.features.vehicle.batterystatus.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository.BatteryRepository
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository.BatteryRepositoryImpl
import com.tejashree.codereviewlab.features.vehicle.batterystatus.presentation.viewmodel.BatteryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val Context.dataStore by preferencesDataStore(name = "battery_prefs")

val batteryStatusModule = module {
    single { androidContext().dataStore }
    single<BatteryRepository> {
        BatteryRepositoryImpl(get())
    }
    viewModelOf(::BatteryViewModel)
}
