package com.yerayyas.marvelsuperheroes.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.yerayyas.marvelsuperheroes.framework.data.database.SuperheroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val SUPERHERO_DATABASE_NAME = "superhero_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context,
    SuperheroDatabase::class.java,SUPERHERO_DATABASE_NAME ).build()

    @Singleton
    @Provides
    fun provideSuperheroDao(db:SuperheroDatabase) = db.getSuperheroDao()
}