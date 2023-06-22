package com.yerayyas.marvelsuperheroes.presentation.di.module

import android.app.Application
import com.yerayyas.marvelsuperheroes.framework.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.framework.data.dataSources.ServerSuperheroDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Provides
    fun provideRemoteDataSource(app: Application): RemoteDataSource {
        return ServerSuperheroDataSource()
    }

}