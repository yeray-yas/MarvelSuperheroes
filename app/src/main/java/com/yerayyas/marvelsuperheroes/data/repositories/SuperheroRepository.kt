package com.yerayyas.marvelsuperheroes.data.repositories

import com.yerayyas.marvelsuperheroes.data.local.SuperheroDao
import com.yerayyas.marvelsuperheroes.data.local.SuperheroEntity
import com.yerayyas.marvelsuperheroes.data.remote.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.domain.model.Superheroes
import com.yerayyas.marvelsuperheroes.domain.model.toDomain
import javax.inject.Inject

class SuperheroRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val superheroDao: SuperheroDao
) {
    suspend fun getSuperheroesFromApi(): List<Superheroes> {
        val response = remoteDataSource.getSuperheroes()
        return response.map { it.toDomain() }
    }

    suspend fun getSuperheroesFromDatabase(): List<Superheroes> {
        val response = superheroDao.getAllSuperheroes()
        return response.map { it.toDomain() }
    }

    suspend fun insertSuperheroes(superheroes:List<SuperheroEntity>) {
        superheroDao.insertSuperheroes(superheroes)
    }

    suspend fun deleteSuperheroes() {
        superheroDao.deleteAllSuperheroes()
    }
}