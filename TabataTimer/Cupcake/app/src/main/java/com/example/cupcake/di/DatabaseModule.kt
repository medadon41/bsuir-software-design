package com.example.cupcake.di

import android.content.Context
import androidx.room.Room
import com.example.cupcake.database.TimerDatabase
import com.example.cupcake.database.TimerDatabaseDao
import com.example.cupcake.database.TimerRepository
import com.example.cupcake.database.TimerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideBookDb(
        @ApplicationContext
        context : Context
    ) = Room.databaseBuilder(
        context,
        TimerDatabase::class.java,
        "timers_database"
    ).build()

    @Provides
    fun provideDatabaseDao(
        timerDatabase: TimerDatabase
    ) = timerDatabase.timerDao()

    @Provides
    fun provideTimerRepository(
        timerDatabaseDao: TimerDatabaseDao
    ): TimerRepository = TimerRepositoryImpl(timerDatabaseDao)
}