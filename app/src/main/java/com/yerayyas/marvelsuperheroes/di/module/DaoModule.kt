package com.yerayyas.marvelsuperheroes.di.module

import android.app.Application
import androidx.room.Room
import com.yerayyas.marvelsuperheroes.data.local.AppDatabase
import com.yerayyas.marvelsuperheroes.data.local.SuperheroDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    fun provideSuperheroDao(application: Application): SuperheroDao {
        val database = Room.databaseBuilder(application, AppDatabase::class.java, "app_database").build()
        return database.superheroDao()
    }
}