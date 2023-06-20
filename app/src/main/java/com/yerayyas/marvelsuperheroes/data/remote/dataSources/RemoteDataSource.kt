package com.yerayyas.marvelsuperheroes.data.remote.dataSources

import com.yerayyas.marvelsuperheroes.data.model.Superhero

interface RemoteDataSource{
    suspend fun getSuperheroes(): List<Superhero>
}