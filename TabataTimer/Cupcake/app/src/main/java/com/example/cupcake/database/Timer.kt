package com.example.cupcake.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timers")
data class Timer(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "timer_id")
    val timerId: Long = 0L,

    @ColumnInfo(name = "timer_name")
    val timerName: String,

    @ColumnInfo(name = "prep_time")
    val prepTime: Int,

    @ColumnInfo(name = "workout_time")
    val workoutTime: Int,

    @ColumnInfo(name = "rest_time")
    val restTime: Int,

    @ColumnInfo(name = "repeats")
    val repeats: Int
)