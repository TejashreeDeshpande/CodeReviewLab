package com.tejashree.codereviewlab.features.vehicle.batterystatus.di

import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository.BatteryRepository
import com.tejashree.codereviewlab.features.vehicle.batterystatus.data.repository.BatteryRepositoryImpl
import com.tejashree.codereviewlab.features.vehicle.batterystatus.presentation.viewmodel.BatteryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val batteryStatusModule = module {
    single<BatteryRepository> {
        BatteryRepositoryImpl()
    }
    viewModelOf(::BatteryViewModel)
}