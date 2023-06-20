package com.yerayyas.marvelsuperheroes.framework.di.module

import com.yerayyas.marvelsuperheroes.data.local.SuperheroDao
import com.yerayyas.marvelsuperheroes.data.remote.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.repository.SuperheroRepository
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun provideSuperheroRepository(
        remoteDataSource: RemoteDataSource,
        superheroDao: SuperheroDao
    ): SuperheroRepository {
        return SuperheroRepository(remoteDataSource, superheroDao)
    }

    @Provides
    fun provideLoadSuperheroesUseCase(repository: SuperheroRepository): LoadSuperheroesUseCase {
        return LoadSuperheroesUseCase(repository)
    }
}
