package com.yerayyas.marvelsuperheroes.usecases

import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoadSuperheroesUseCase(private val repository: SuperheroRepository) {

    suspend fun invoke(): List<Superhero> = withContext(Dispatchers.IO) {

        repository.getSuperheroes()


    }

}