package com.yerayyas.marvelsuperheroes.domain.states

sealed interface Failure {
    data class NetworkError(val message: String?) : Failure
    data class ServerError(val code: Int, val message: String?) : Failure
    data class UnknownError(val message: String?) : Failure
}