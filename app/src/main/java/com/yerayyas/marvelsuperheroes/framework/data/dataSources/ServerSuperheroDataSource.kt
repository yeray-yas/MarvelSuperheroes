package com.yerayyas.marvelsuperheroes.framework.data.dataSources

import com.yerayyas.marvelsuperheroes.BuildConfig
import com.yerayyas.marvelsuperheroes.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.network.SuperheroDbClient

class ServerSuperheroDataSource : RemoteDataSource {


    override suspend fun getSuperheroes(): List<Superhero> {
        return SuperheroDbClient.service
            .getCharacters(BuildConfig.TS, BuildConfig.API_KEY, BuildConfig.HASH).data.superheroes
    }
}