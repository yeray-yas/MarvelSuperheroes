package com.yerayyas.marvelsuperheroes.framework

import com.yerayyas.marvelsuperheroes.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.framework.data.dataSources.ServerSuperheroDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Provides
    fun provideRemoteDataSource(): RemoteDataSource {
        return ServerSuperheroDataSource()
    }

}