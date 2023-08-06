package com.yerayyas.marvelsuperheroes.domain.states

sealed class Result<out V, out E : Failure> {
    object Loading : Result<Nothing, Nothing>()
    data class Success<out V>(val value: V) : Result<V, Nothing>()
    data class Error<out E : Failure>(val failure: E) : Result<Nothing, E>()
}

val Result<*, *>.isSuccess: Boolean
    get() = this is Result.Success<*>