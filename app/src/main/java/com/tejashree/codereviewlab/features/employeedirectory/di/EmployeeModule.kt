package com.tejashree.codereviewlab.features.employeedirectory.di

import com.tejashree.codereviewlab.features.employeedirectory.data.repository.EmployeesRepositoryImpl
import com.tejashree.codereviewlab.features.employeedirectory.data.service.EmployeesApiService
import com.tejashree.codereviewlab.features.employeedirectory.domain.repository.EmployeesRepository
import com.tejashree.codereviewlab.features.employeedirectory.presentation.viewmodel.EmployeesViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


val employeeModule = module {

    single<OkHttpClient> {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    single<Retrofit> {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .baseUrl("https://s3.amazonaws.com/")
            .client(get())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    single<EmployeesApiService> { get<Retrofit>().create(EmployeesApiService::class.java) }

    single<EmployeesRepository> {
        EmployeesRepositoryImpl(get())
    }
    viewModelOf(::EmployeesViewModel)
}
