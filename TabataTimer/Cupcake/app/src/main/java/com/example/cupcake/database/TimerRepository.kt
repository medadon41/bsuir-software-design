package com.example.cupcake.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

interface TimerRepository {
    suspend fun getTimer(id: Int): Flow<Timer?>

    suspend fun getAllTimers(): Flow<List<Timer>>

    suspend fun addTimer(timer: Timer)

    suspend fun updateTimer(timer: Timer)

    suspend fun deleteTimer(timer: Timer)

    suspend fun deleteAllTimers()
}