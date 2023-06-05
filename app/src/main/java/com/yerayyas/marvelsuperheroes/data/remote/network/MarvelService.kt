package com.yerayyas.marvelsuperheroes.data.remote.network

import com.yerayyas.marvelsuperheroes.domain.model.Marvel
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("v1/public/characters")
    suspend fun getCharacters(
        @Query("ts") ts: Int,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Marvel
}