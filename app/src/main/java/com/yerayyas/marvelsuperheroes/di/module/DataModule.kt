package com.yerayyas.marvelsuperheroes.di.module

import com.yerayyas.marvelsuperheroes.data.local.AppDatabase
import com.yerayyas.marvelsuperheroes.data.local.SuperheroDao
import com.yerayyas.marvelsuperheroes.data.remote.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideSuperheroRepository(remoteDataSource: RemoteDataSource,
                                   superheroDao: SuperheroDao
    ):SuperheroRepository{
        return SuperheroRepository(remoteDataSource, superheroDao)
    }

}