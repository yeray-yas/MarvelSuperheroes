package com.yerayyas.marvelsuperheroes.framework.ui.main

import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import com.yerayyas.marvelsuperheroes.usecases.LoadSuperheroesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
class MainActivityModule {

    @Singleton
    @Provides
    fun provideLoadSuperheroesUseCase(repository: SuperheroRepository): LoadSuperheroesUseCase {
        return LoadSuperheroesUseCase(repository)
    }
}