package com.yerayyas.marvelsuperheroes.usecases

import com.yerayyas.marvelsuperheroes.data.model.Superhero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoadSuperheroesUseCase() {

    suspend fun invoke(): List<Superhero> = withContext(Dispatchers.IO) {


        delay(2000)
        repository.getSuperheroes()


    }

}