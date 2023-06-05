package com.yerayyas.marvelsuperheroes.framework.di.module

import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import com.yerayyas.marvelsuperheroes.domain.usecases.LoadSuperheroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun provideLoadSuperheroesUseCase(repository: SuperheroRepository): LoadSuperheroesUseCase {
        return LoadSuperheroesUseCase(repository)
    }
}