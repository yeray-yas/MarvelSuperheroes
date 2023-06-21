package com.yerayyas.marvelsuperheroes.di.module

import android.content.Context
import androidx.room.Room
import com.yerayyas.marvelsuperheroes.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val SUPERHERO_DATABASE_NAME = "superhero_table"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java,
            SUPERHERO_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideSuperheroDao(db:AppDatabase) = db.superheroDao()
}