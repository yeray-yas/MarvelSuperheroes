package com.yerayyas.marvelsuperheroes.di.module

import android.app.Application
import com.yerayyas.marvelsuperheroes.data.local.SuperheroDao
import com.yerayyas.marvelsuperheroes.data.remote.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.remote.dataSources.ServerSuperheroDataSource
import com.yerayyas.marvelsuperheroes.data.repository.SuperheroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideSuperheroRepository(
        remoteDataSource: RemoteDataSource,
        superheroDao: SuperheroDao
    ): SuperheroRepository {
        return SuperheroRepository(remoteDataSource, superheroDao)
    }

    @Provides
    fun provideRemoteDataSource(app: Application): RemoteDataSource {
        return ServerSuperheroDataSource()
    }
}

