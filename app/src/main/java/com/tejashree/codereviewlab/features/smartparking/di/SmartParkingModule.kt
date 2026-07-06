package com.tejashree.codereviewlab.features.smartparking.di

import com.tejashree.codereviewlab.features.smartparking.presentation.viewmodel.ParkingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val smartParkingModule = module {
    viewModelOf(::ParkingViewModel)
}
