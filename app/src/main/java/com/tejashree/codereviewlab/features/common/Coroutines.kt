package com.tejashree.codereviewlab.features.common

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.milliseconds

class Coroutines {

    /*
    // Launch
    viewModelScope.launch {
        println("Loading User")
    }
    // Async-Await
    val deferred = viewModelScope.async {
        repository.getUser()
    }
    val user = deferred.await()

    // coroutineScope
    // If one child fails -> entire scope fails
    suspend fun loadData() = coroutineScope {
        launch {
            println("Task 1")
        }
        launch {
            println("Task 2")
        }
    }

    // supervisorScope
    // Failure of one child doesn't cancel siblings
    suspend fun loadDashboard() = supervisorScope {
        launch {
            error("API failed")
        }
        launch {
            println("Weather Loaded")
        }
    }

    // Parallel API Calls
    suspend fun loadDashboard() = coroutineScope {
        val weather = async { api.weather() }
        val news = async { api.news() }
        val stocks = async { api. stocks() }

        Dashboard(
            weather.await()
            news.await()
            stocks.await()
        )
    }

    // Sequential Calls
    suspend fun login() {
        val token = api.login()
        val profile = api.profile(token)
        val settings = api.settings(profile.id)
    }

    // Cancel a Coroutine
    val job = scope.launch {
        repeat(1000) {
            delay(100.milliseconds)
            println(it)
        }
    }
    job.cancel()

    // Cancel & Wait
    job.cancelAndJoin()

    // Timeout
    val result = withTimeoutOrNull(3000) {
        api.fetch()
    }

    // switch Thread
    withContext(Dispatchers.IO) {
        repository.load()
    }

    withContext(Dispatchers.Default) {
        heavyCalculation()
    }

    withContext(Dispatchers.Main) {
        updateUI()
    }

    // Mutex
    // Only 1 coroutine enters at a time
    private val mutex = Mutex()
    var counter = 0
    suspend fun increment() {
        mutex.withLock {
            counter++
        }
    }
    //


*/
}