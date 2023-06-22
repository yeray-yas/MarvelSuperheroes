package com.yerayyas.marvelsuperheroes.domain.states

sealed class Result<out V, out E : Failure> {
    object Loading : Result<Nothing, Nothing>()
    data class Success<out V>(val value: V) : Result<V, Nothing>()
    data class Error<out E : Failure>(val failure: E) : Result<Nothing, E>()


    val isSuccess: Boolean
        get() = this is Success<V>

    val isError: Boolean
        get() = this is Error<E>
}
