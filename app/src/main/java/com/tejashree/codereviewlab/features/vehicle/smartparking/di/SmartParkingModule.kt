package com.tejashree.codereviewlab.features.vehicle.smartparking.di

import com.tejashree.codereviewlab.features.vehicle.smartparking.presentation.viewmodel.SmartParkingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val smartParkingModule = module {
    viewModelOf(::SmartParkingViewModel)
}
