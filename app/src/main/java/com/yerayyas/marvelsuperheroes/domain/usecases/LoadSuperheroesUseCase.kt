package com.yerayyas.marvelsuperheroes.domain.usecases

import com.yerayyas.marvelsuperheroes.domain.model.Superhero
import com.yerayyas.marvelsuperheroes.data.repositories.SuperheroRepository
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
        } catch (e:IOException) {
            emit(Result.Error(Failure.NetworkError(e.message)))
        } catch (e:HttpException) {
            emit(Result.Error(Failure.ServerError(e.code(), e.message)))
        } catch (otherException: Exception) {
            emit(Result.Error(Failure.UnknownError(otherException.message)))
        }
    }.flowOn(Dispatchers.IO)
}
