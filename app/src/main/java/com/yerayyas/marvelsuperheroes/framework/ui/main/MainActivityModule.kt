package com.yerayyas.marvelsuperheroes.framework.ui.main

import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import com.yerayyas.marvelsuperheroes.usecases.LoadSuperheroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun provideLoadSuperheroesUseCase(repository: SuperheroRepository): LoadSuperheroesUseCase {
        return LoadSuperheroesUseCase(repository)
    }
}