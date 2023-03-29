package com.yerayyas.marvelsuperheroes.data.network

import com.example.superheroesopenbank.data.model.Marvel
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("ts") ts: Int,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): Marvel
}