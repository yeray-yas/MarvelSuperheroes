package com.yerayyas.marvelsuperheroes.data.repositories

import com.yerayyas.marvelsuperheroes.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.model.Superhero

class SuperheroRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getSuperheroes(): List<Superhero> =  remoteDataSource.getSuperheroes()

}