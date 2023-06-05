package com.yerayyas.marvelsuperheroes.data.remote.dataSources

import com.yerayyas.marvelsuperheroes.BuildConfig
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.data.remote.network.SuperheroDbClient

class ServerSuperheroDataSource : RemoteDataSource {


    override suspend fun getSuperheroes(): List<Superhero> {
        return SuperheroDbClient.service
            .getCharacters(BuildConfig.TS, BuildConfig.API_KEY, BuildConfig.HASH).data.superheroes
    }
}