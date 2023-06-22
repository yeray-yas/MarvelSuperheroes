package com.yerayyas.marvelsuperheroes.domain.usecases

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
import retrofit2.HttpException
import java.io.IOException

class LoadSuperheroesUseCase @Inject constructor(private val repository: SuperheroRepository) {

    suspend fun invoke(): Flow<Result<List<Super>, Failure>> = flow {
        emit(Result.Loading)

        try {
            val superheroes = repository.getSuperheroesFromApi()
            if (superheroes.isNotEmpty()){
                //
            }else{
                repository.getSuperheroesFromDatabase()
            }
            emit(Result.Success(superheroes))
        } catch (networkException:IOException) {
            emit(Result.Error(Failure.NetworkError(networkException.message)))
        } catch (serverException:HttpException) {
            emit(Result.Error(Failure.ServerError(serverException.code(), serverException.message)))
        } catch (otherException: Exception) {
            emit(Result.Error(Failure.UnknownError(otherException.message)))
        }
    }.flowOn(Dispatchers.IO)
}
