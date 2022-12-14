package com.example.cupcake.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TimerDatabaseDao {
    @Query("SELECT * FROM timers")
    fun getAll(): Flow<List<Timer>>

    @Query("SELECT * FROM timers WHERE timer_id = :id")
    fun getById(id: Int): Flow<Timer?>

    @Insert
    fun insert(item: Timer)

    @Update
    fun update(item: Timer)

    @Delete
    fun delete(item: Timer)

    @Query("DELETE FROM timers")
    fun deleteAll()
}