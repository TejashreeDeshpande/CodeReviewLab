package com.tejashree.codereviewlab.features.vehicle.parkandgo.di

import com.tejashree.codereviewlab.features.vehicle.parkandgo.presentation.viewmodel.ParkGoViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val parkGoAppModule = module{
    viewModelOf(::ParkGoViewModel)
}