package com.yerayyas.marvelsuperheroes.data.repositories

import com.yerayyas.marvelsuperheroes.framework.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import javax.inject.Inject

class SuperheroRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {
    suspend fun getSuperheroes(): List<Superhero> =  remoteDataSource.getSuperheroes()
}