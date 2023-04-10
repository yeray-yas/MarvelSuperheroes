package com.yerayyas.marvelsuperheroes.framework.data.dataSources

import com.yerayyas.marvelsuperheroes.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.network.SuperheroDbClient
import com.yerayyas.marvelsuperheroes.framework.ui.common.Constants.API_KEY
import com.yerayyas.marvelsuperheroes.framework.ui.common.Constants.HASH
import com.yerayyas.marvelsuperheroes.framework.ui.common.Constants.TS

class ServerSuperheroDataSource : RemoteDataSource {
    override suspend fun getSuperheroes(): List<Superhero> {
        return SuperheroDbClient.service
            .getCharacters(TS, API_KEY, HASH).data.superheroes
    }
}