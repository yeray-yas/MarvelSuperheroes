package com.yerayyas.marvelsuperheroes.data.repositories

import com.yerayyas.marvelsuperheroes.domain.model.Super
import com.yerayyas.marvelsuperheroes.domain.model.toDomain
import com.yerayyas.marvelsuperheroes.framework.data.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.framework.data.database.dao.SuperheroDao
import com.yerayyas.marvelsuperheroes.framework.data.database.entities.SuperheroEntity
import javax.inject.Inject

class SuperheroRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val superheroDao: SuperheroDao
) {
    suspend fun getSuperheroesFromApi(): List<Super> {

        val response = remoteDataSource.getSuperheroes()
        return response.map { it.toDomain() }
    }

    suspend fun getSuperheroesFromDatabase(): List<Super>{
        val response = superheroDao.getAllSuperheroes()
        return response.map { it.toDomain() }
    }

    suspend fun insertSuperheroes(superheroes:List<SuperheroEntity>) {
        superheroDao.insertAll(superheroes)
    }

    suspend fun clearSuperheroes() {
        superheroDao.deleteSuperheroes()
    }
}