package com.yerayyas.marvelsuperheroes.domain.usecases

import com.yerayyas.marvelsuperheroes.data.model.Superhero
import com.yerayyas.marvelsuperheroes.data.repository.SuperheroRepository
import com.yerayyas.marvelsuperheroes.framework.states.Failure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.yerayyas.marvelsuperheroes.framework.states.Result
import retrofit2.HttpException
import java.io.IOException

class LoadSuperheroesUseCase @Inject constructor(private val repository: SuperheroRepository) {

    suspend fun invoke(): Flow<Result<List<Superhero>, Failure>> = flow {
        emit(Result.Loading)

        try {
            val superheroes = repository.getSuperheroes()
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
