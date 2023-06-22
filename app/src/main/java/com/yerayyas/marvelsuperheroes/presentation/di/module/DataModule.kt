package com.yerayyas.marvelsuperheroes.presentation.di.module

import com.yerayyas.marvelsuperheroes.framework.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideSuperheroRepository(remoteDataSource: RemoteDataSource):SuperheroRepository{
        return SuperheroRepository(remoteDataSource)
    }
}