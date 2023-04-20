package com.yerayyas.marvelsuperheroes.usecases

import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadSuperheroesUseCase @Inject constructor(private val repository: SuperheroRepository) {

    suspend fun invoke(): List<Superhero> = withContext(Dispatchers.IO) {

        repository.getSuperheroes()


    }
}