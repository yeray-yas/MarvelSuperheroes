package com.yerayyas.marvelsuperheroes.data.network

import com.yerayyas.marvelsuperheroes.framework.ui.common.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SuperheroDbClient {  //This is a singleton. It will make only one retrofit instance


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val service: MarvelService = retrofit.create(MarvelService::class.java)
}