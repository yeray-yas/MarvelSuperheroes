package com.yerayyas.marvelsuperheroes.data.repository

import com.yerayyas.marvelsuperheroes.data.local.SuperheroDao
import com.yerayyas.marvelsuperheroes.data.local.SuperheroEntity
import com.yerayyas.marvelsuperheroes.data.remote.dataSources.RemoteDataSource
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.model.Thumbnail
import javax.inject.Inject

class SuperheroRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val superheroDao: SuperheroDao
) {
    suspend fun getSuperheroes(): List<Superhero> {
        val superheroesFromDb = superheroDao.getAllSuperheroes()
        return if (superheroesFromDb.isNotEmpty()) {
            superheroesFromDb.map { superheroEntity ->
                Superhero(
                    id = superheroEntity.id,
                    name = superheroEntity.name,
                    description = superheroEntity.description,
                    thumbnail = Thumbnail(extension = "jpg", path = superheroEntity.thumbnailUrl),
                    comics = superheroEntity.comics,

                )
            }
        } else {
            val superheroesFromRemote = remoteDataSource.getSuperheroes()
            val superheroEntities = superheroesFromRemote.map { superhero ->
                SuperheroEntity(
                    id = superhero.id,
                    name = superhero.name,
                    description = superhero.description,
                    thumbnailUrl = "${superhero.thumbnail.path}.${superhero.thumbnail.extension}",
                    comics = superhero.comics
                )
            }
            superheroDao.insertSuperheroes(superheroEntities)
            superheroesFromRemote
        }
    }
}
