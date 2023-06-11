package com.yerayyas.marvelsuperheroes.framework.states

import com.yerayyas.marvelsuperheroes.domain.model.Superhero

sealed class MainUIState {
    object Loading : MainUIState()
    data class Success(val data: List<Superhero>) : MainUIState()
    sealed class Error:MainUIState(){
        data class NetworkError(val message: String) : Error()
        data class ServerError(val code: Int, val message: String) : Error()

        data class UnknownError(val message: String) : Error()

    }
}