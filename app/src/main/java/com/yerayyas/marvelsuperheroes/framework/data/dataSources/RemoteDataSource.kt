package com.yerayyas.marvelsuperheroes.framework.data.dataSources

import com.yerayyas.marvelsuperheroes.data.model.Superhero

interface RemoteDataSource{
    suspend fun getSuperheroes(): List<Superhero>
}