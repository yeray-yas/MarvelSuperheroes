package com.yerayyas.marvelsuperheroes.data.remote.dataSources

import com.yerayyas.marvelsuperheroes.domain.model.Superhero

interface RemoteDataSource{
    suspend fun getSuperheroes(): List<Superhero>
}