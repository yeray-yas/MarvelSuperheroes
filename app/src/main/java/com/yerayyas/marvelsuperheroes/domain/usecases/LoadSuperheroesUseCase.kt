package com.yerayyas.marvelsuperheroes.domain.usecases

import android.util.Log
import android.widget.Toast
import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
import com.yerayyas.marvelsuperheroes.domain.model.Super
import com.yerayyas.marvelsuperheroes.domain.states.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.yerayyas.marvelsuperheroes.domain.states.Result
import com.yerayyas.marvelsuperheroes.framework.data.database.entities.toDatabase
import kotlinx.coroutines.currentCoroutineContext
import retrofit2.HttpException
import java.io.IOException

class LoadSuperheroesUseCase @Inject constructor(private val repository: SuperheroRepository) {

    suspend fun invoke(): Flow<Result<List<Super>, Failure>> = flow {
        emit(Result.Loading)

        try {
            var superheroes = repository.getSuperheroesFromApi()
            if (superheroes.isNotEmpty()) {
                repository.clearSuperheroes()
                repository.insertSuperheroes(superheroes.map { it.toDatabase() })
                emit(Result.Success(superheroes))
                Log.i("YDS", "Datos cargados desde la API")
            } else {
                superheroes = repository.getSuperheroesFromDatabase()
                emit(Result.Success(superheroes))
                Log.i("YDS", "Datos cargados desde la base de datos")
            }
        } catch (networkException: IOException) {
            val superheroes = repository.getSuperheroesFromDatabase()
            emit(Result.Success(superheroes))
            emit(Result.Error(Failure.NetworkError(networkException.message)))
            Log.i("YDS", "Error de red. Datos cargados desde la base de datos")
        } catch (serverException: HttpException) {
            emit(Result.Error(Failure.ServerError(serverException.code(), serverException.message)))
        } catch (otherException: Exception) {
            emit(Result.Error(Failure.UnknownError(otherException.message)))
        }
    }.flowOn(Dispatchers.IO)
}


