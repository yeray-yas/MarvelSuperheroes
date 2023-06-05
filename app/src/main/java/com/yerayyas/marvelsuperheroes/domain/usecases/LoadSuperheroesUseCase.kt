package com.yerayyas.marvelsuperheroes.domain.usecases

import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoadSuperheroesUseCase @Inject constructor(private val repository: SuperheroRepository) {

    suspend fun invoke(): Flow<List<Superhero>> = flow {

        emit(repository.getSuperheroes())


    }.flowOn(Dispatchers.IO)
}