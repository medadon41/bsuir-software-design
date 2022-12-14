package com.example.cupcake.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TimerRepositoryImpl(private val timerDatabaseDao: TimerDatabaseDao): TimerRepository {

    val readAllData: Flow<List<Timer>> = timerDatabaseDao.getAll()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override suspend fun getAllTimers(): Flow<List<Timer>> {
        return timerDatabaseDao.getAll()
    }

    override suspend fun getTimer(id: Int): Flow<Timer?> {
        return timerDatabaseDao.getById(id)
    }

    override suspend fun addTimer(timer: Timer) {
        timerDatabaseDao.insert(timer)
    }

    override suspend fun updateTimer(timer: Timer) {
        timerDatabaseDao.update(timer)
    }

    override suspend fun deleteTimer(timer: Timer) {
        timerDatabaseDao.delete(timer)
    }

    override suspend fun deleteAllTimers() {
        timerDatabaseDao.deleteAll()
    }
}